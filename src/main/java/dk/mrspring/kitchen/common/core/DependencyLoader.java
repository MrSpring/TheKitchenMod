package dk.mrspring.kitchen.common.core;

import cpw.mods.fml.common.versioning.ComparableVersion;
import cpw.mods.fml.relauncher.FMLInjectionData;
import cpw.mods.fml.relauncher.IFMLCallHook;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import sun.misc.URLClassPath;
import sun.net.util.URLUtil;

import java.io.Closeable;
import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Created on 14-03-2016 for TheKitchenMod.
 */
@SuppressWarnings("ALL")
public class DependencyLoader implements IFMLCallHook
{
    String repo = "mrspring.dk";
    String[] dependencies = new String[]{
            "dk.mrspring:NBTUtils:1.0.1B-1.7.10"
    };
    List<Artifact> found = new ArrayList<Artifact>();
    List<String> toDownload = new ArrayList<String>();

    LaunchClassLoader loader;

    private final FilenameFilter LIB_FILTER = new SuffixFileFilter(new String[]{".jar", ".zip"}, IOCase.INSENSITIVE);

    String mcVersion;

    File mcDir, modDir;

    File modFile;

    public DependencyLoader() throws Exception
    {
        Object[] data = FMLInjectionData.data();
        this.mcVersion = (String) data[4];
        this.mcDir = (File) data[6];
        this.modDir = new File(mcDir, "mods");
        this.modFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        this.loader = (LaunchClassLoader) getClass().getClassLoader();

        System.out.println("Loaded dependencies!");
    }

    private void load()
    {
        this.scanDir(modDir);
        this.scanDir(new File(modDir, mcVersion));
        this.compareAndRemoveOutdated();
        this.downloadMissing();
    }

    private void scanDir(File dir)
    {
        File[] files = dir.listFiles(LIB_FILTER);
        if (files != null) for (File f : files) this.scanFile(f);
    }

    private void scanFile(File file)
    {
        JarFile jar = null;
        try
        {
            jar = new JarFile(file);
            Manifest manifest = jar.getManifest();
            Attributes attributes = manifest.getMainAttributes();
            String artifactId = attributes.getValue("ArtifactID");
            if (artifactId != null) found.add(new Artifact(artifactId, file));
        } catch (Exception e)
        {
            System.err.println("Failed to look at: " + file.toString());
            e.printStackTrace();
        } finally
        {
            if (jar != null) try
            {
                jar.close();
            } catch (Exception ignored)
            {
            }
        }
    }

    private void compareAndRemoveOutdated()
    {
        for (String d : dependencies)
            compare(new Artifact(d, null));
    }

    private void compare(Artifact artifact)
    {
        for (Artifact a : found)
        {
            if (!a.equals(artifact)) continue;
            int compared = compareArtifacts(a.artifactId, artifact.artifactId);
            if (compared < 0)
            {
                toDownload.add(artifact.artifactId);
                delete(a.file);
                found.remove(a);
            }
            return;
        }
        toDownload.add(artifact.artifactId);
    }

    private void downloadMissing()
    {
        for (String s : toDownload)
        {
            Artifact artifact = new Artifact(s, null);
            download(artifact);
        }
    }

    private void download(Artifact artifact)
    {
        try
        {
            artifact.makeFile();
            FileUtils.copyURLToFile(artifact.makeURL(), artifact.file);
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            if (artifact.file == null || !artifact.file.exists())
//                injectIntoClasspath(artifact.file);
                throw new RuntimeException("Artifact: " + artifact.artifactId + " did not download!");
        }
    }

    private void injectIntoClasspath(File file)
    {
        try
        {
            loader.addURL(file.toURI().toURL());
        } catch (MalformedURLException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void delete(File file)
    {
        if (file.delete()) return;
        try
        {
            URL url = file.toURI().toURL();
            Field f_ucp = URLClassLoader.class.getDeclaredField("ucp");
            Field f_loaders = URLClassPath.class.getDeclaredField("loaders");
            Field f_lmap = URLClassPath.class.getDeclaredField("lmap");
            f_ucp.setAccessible(true);
            f_loaders.setAccessible(true);
            f_lmap.setAccessible(true);

            URLClassPath ucp = (URLClassPath) f_ucp.get(loader);
            Closeable loader = ((Map<String, Closeable>) f_lmap.get(ucp)).remove(URLUtil.urlNoFragString(url));
            if (loader != null)
            {
                loader.close();
                ((List<?>) f_loaders.get(ucp)).remove(loader);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        if (!file.delete())
        {
            file.deleteOnExit();
            if (file.exists())
                System.err.println("Failed to remove outdated dependency! Make sure this file is removed: " +
                        file.getAbsolutePath());
            System.exit(1);
        }

    }

    private final String URL_FORMAT = "/maven/%1$s/%2$s/%3$s/%2$s-%3$s.%4$s";
    private final String FILE_FORMAT = "%2$s-%3$s.%4$s";

    private class Artifact
    {
        String artifactId;

        String group, name, version;

        File file;

        Artifact(String artifactId, File file)
        {
            this.artifactId = artifactId;
            this.file = file;
            String[] split = artifactId.split(":", 4);
            this.group = split[0];
            this.name = split[1];
            this.version = split[2];
        }

        public URL makeURL() throws MalformedURLException
        {
            return new URL("https", repo, String.format(URL_FORMAT,
                    group.replace(".", "/"),
                    name,
                    version,
                    "jar"));
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj instanceof String) return artifactId.equals(obj);
            else if (obj instanceof Artifact)
            {
                Artifact that = (Artifact) obj;
                return this.group.equals(that.group) && this.name.equals(that.name);
            } else return false;
        }

        public void makeFile()
        {
            this.file = new File(new File(modDir, mcVersion), String.format(FILE_FORMAT,
                    group.replace(".", "/"),
                    name,
                    version,
                    "jar"
            ));
        }
    }

    private int compareArtifacts(String a1, String a2)
    {
        return makeFromArtifact(a1).compareTo(makeFromArtifact(a2));
    }

    private ComparableVersion makeFromArtifact(String artifact)
    {
        String v = artifact.split(":")[2];
        if (v.endsWith("-" + mcVersion)) v = v.substring(0, v.length() - mcVersion.length() - 1);
        return new ComparableVersion(v);
    }

    private ComparableVersion makeFromArtifact(Artifact artifact)
    {
        return makeFromArtifact(artifact.artifactId);
    }

    @Override
    public void injectData(Map<String, Object> data)
    {
    }

    @Override
    public Void call() throws Exception
    {
        this.load();
        return null;
    }
}
