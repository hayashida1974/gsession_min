package jp.groupsession.v2.restapi.filter;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.jar.JarFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.restapi.filter.annotation.ActionFilter;

public class RestApiActionFilterLoader {
    /** ロガークラス*/
    private static Log log__ = LogFactory.getLog(RestApiActionFilterLoader.class);

    /**
     *
     * <br>[機  能] ActionFilterクラスを探索し返す
     * <br>[解  説]
     * <br>[備  考]
     * @param classLoader
     * @return ActionFilterクラス
     */
    public  List<Class<?>> load(ClassLoader classLoader) {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (classLoader instanceof URLClassLoader) {
            Arrays
            .stream(((URLClassLoader) classLoader).getURLs())
            .flatMap(url -> {
                log__.debug(url.toExternalForm());

                try {
                    return __findClasses(url, classLoader)
                    .stream();
                } catch (Exception e) {
                    log__.error(e);
                    throw new RuntimeException(e);
                }
            })
            .forEach(clz -> {
                log__.debug("load filter :" + clz.getName());
                classes.add(clz);
            });
        }
        return classes;
    }
    /**
     *
     * <br>[機  能] ActionFilterクラスの探索
     * <br>[解  説]
     * <br>[備  考]
     * @param url
     * @param classLoader
     * @return ActionFilterクラス
     * @throws Exception
     */
    private  List<Class<?>> __findClasses(
            URL url,
            ClassLoader classLoader) throws Exception {

        if (url == null) {
            return new ArrayList<Class<?>>();
        }
        log__.debug(url.toExternalForm());
        log__.debug("url:" + url);

        String protocol = url.getProtocol();
        if (url.toString().endsWith("jar")) {
            return __findClassesWithJarFile("", url, classLoader);
        }
        if ("file".equals(protocol)) {
            return __findClassesWithFile("", Paths.get(url.toURI()).toFile(), classLoader);
        }

        throw new IllegalArgumentException("Unsupported Class Load Protodol[" + protocol + "]");
    }

    private static String fileNameToClassName(String name) {
        return name.substring(0, name.length() - ".class".length());
    }

    private static String resourceNameToClassName(String resourceName) {
        return fileNameToClassName(resourceName).replace('/', '.');
    }
    /**
     *
     * <br>[機  能] ActionFilterクラスの探索
     * <br>[解  説] classes フォルダから
     * <br>[備  考]
     * @param packageName
     * @param dir
     * @param classLoader
     * @return ActionFilterクラス
     * @throws Exception
     */
    private  List<Class<?>> __findClassesWithFile(
            String packageName, File dir, ClassLoader classLoader) throws Exception {
        List<Class<?>> classes = new ArrayList<Class<?>>();


        for (String path : dir.list()) {
            File entry = new File(dir, path);
            if (entry.isFile() && entry.getName().endsWith(".class")) {
                Class<?> type = null;
                try {
                    if (packageName.length() == 0) {
                        type = classLoader.loadClass(
                                fileNameToClassName(entry.getName()));
                    } else {
                        type = classLoader.loadClass(
                                        packageName
                                            + "." + fileNameToClassName(
                                                    entry.getName()
                                                    )
                                            );
                    }
                } catch (NoClassDefFoundError e) {
                    log__.debug(e);
                } catch (ClassNotFoundException e) {
                    log__.debug(e);
                }
                if (type != null) {
                    log__.debug(type.getName());
                }
                Optional.ofNullable(type)
                    .filter(clz -> clz.isAnnotationPresent(ActionFilter.class))
                    .ifPresent(clz -> classes.add(clz));

            } else if (entry.isDirectory()) {
                if (packageName.length() == 0) {
                    classes.addAll(__findClassesWithFile(entry.getName(), entry, classLoader));
                    continue;
                }
                classes.addAll(__findClassesWithFile(packageName
                        + "." + entry.getName(), entry, classLoader));
            }
        }

        return classes;
    }
    /**
     *
     * <br>[機  能] パッケージ名をリソースパスへ変換
     * <br>[解  説]
     * <br>[備  考]
     * @param packageName
     * @return リソースパス
     */
    private  String __packageNameToResourceName(String packageName) {
        return packageName.replace('.', '/');
    }
    /**
     *
     * <br>[機  能] ActionFilterクラスの探索
     * <br>[解  説] jar ファイルから
     * <br>[備  考]
     * @param rootPackageName
     * @param url
     * @param classLoader
     * @return ActionFilterクラス
     * @throws Exception
     */
    private  List<Class<?>> __findClassesWithJarFile(
            String rootPackageName, URL url, ClassLoader classLoader) throws Exception {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        String packageNameAsResourceName = __packageNameToResourceName(rootPackageName);

        try (JarFile jarFile = new JarFile(Paths.get(url.toURI()).toFile())) {
            jarFile.stream().forEach(entry -> {
                //log__.debug(entry.getName());
                Optional.ofNullable(entry)
                    .filter(ent ->
                        (ent.getName().startsWith(packageNameAsResourceName)
                        && ent.getName().endsWith(".class"))
                        )
                    .map(ent -> {
                        try {
                            return classLoader.loadClass(
                                    resourceNameToClassName(ent.getName()));
                        } catch (NoClassDefFoundError e) {
                            log__.debug(e);
                        } catch (ClassNotFoundException e) {
                            log__.debug(e);
                        }
                        return null;
                    })
                    .filter(type -> type.isAnnotationPresent(ActionFilter.class))
                    .ifPresent(type -> classes.add(type));

            });

        }

        return classes;
    }

}