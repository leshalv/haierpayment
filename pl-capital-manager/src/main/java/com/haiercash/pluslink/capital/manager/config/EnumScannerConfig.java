package com.haiercash.pluslink.capital.manager.config;

import com.google.common.collect.Maps;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by lihua on 2016/7/20.
 */
@Configuration
public class EnumScannerConfig implements BeanDefinitionRegistryPostProcessor, InitializingBean, ApplicationContextAware {
//    private String basePackage = "com.haiercash.pluslink.capital.common.enums";
    private String basePackage = "com.haiercash.pluslink.capital.enums";
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        Scanner scanner = new Scanner(beanDefinitionRegistry);
        scanner.setResourceLoader(this.applicationContext);

        scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    private final class Scanner extends ClassPathBeanDefinitionScanner {
        public Scanner(BeanDefinitionRegistry registry) {
            super(registry);
        }

        /**
         * Configures parent scanner to search for the right interfaces. It can
         * search for all classes or just for those that extends a
         * markerInterface or/and those annotated with the annotationClass
         */
        @Override
        protected void registerDefaultFilters() {
            boolean acceptAllClasses = true;

            if (acceptAllClasses) {
                // default include filter that accepts all classes
                addIncludeFilter(new TypeFilter() {
                    public boolean match(MetadataReader metadataReader,
                                         MetadataReaderFactory metadataReaderFactory)
                            throws IOException {
                        return true;
                    }
                });
            }

            // exclude package-info.java
            addExcludeFilter(new TypeFilter() {
                public boolean match(MetadataReader metadataReader,
                                     MetadataReaderFactory metadataReaderFactory)
                        throws IOException {
                    String className = metadataReader.getClassMetadata()
                            .getClassName();
                    return className.endsWith("package-info");
                }
            });
        }

        /**
         * Calls the parent search that will search and register all the
         * candidates. Then the registered objects are post processed to set
         * them as MapperFactoryBeans
         */
        @Override
        protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
            // 创建一个集合，存放扫描到Bean定义的封装类
            // 遍历扫描所有给定的包
            for (String basePackage : basePackages) {
                // 调用父类ClassPathScanningCandidateComponentProvider的方法
                // 扫描给定类路径，获取符合条件的Bean定义
                Set<BeanDefinition> candidates = super.findCandidateComponents(basePackage);
                // 遍历扫描到的Bean
                for (BeanDefinition candidate : candidates) {
                    AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidate;
                    if (beanDefinition != null) try {
                        Class<?> cls = Class.forName(beanDefinition.getBeanClassName());
                        if (cls.getSuperclass() != null) {
                            String superClassName = cls.getSuperclass().getName();
                            if (superClassName != null && superClassName.equals("java.lang.Enum")) {
                                // 枚举
                                Method valuesMethod = cls.getMethod("values");
                                if (valuesMethod != null) {
                                    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                                    Object[] enumObjects = (Object[]) valuesMethod.invoke(null);
                                    if (enumObjects != null && enumObjects.length > 0) {
                                        for (Object enumObject : enumObjects) {
                                            Method getCodeMethod = enumObject.getClass().getMethod("getCode");
                                            Method getDescMethod = enumObject.getClass().getMethod("getDesc");
                                            Method nameMethod = enumObject.getClass().getMethod("name");
                                            String code = (String) getCodeMethod.invoke(enumObject);
                                            String desc = (String) getDescMethod.invoke(enumObject);
                                            String name = (String) nameMethod.invoke(enumObject);
                                            Map<String, String> map = Maps.newHashMap();
                                            map.put("name", name);
                                            map.put("code", code);
                                            map.put("desc", desc);
                                            list.add(map);
                                        }
                                    }
                                    String name = cls.getSimpleName();
                                    Global.enumsMap.put(name.substring(0, 1).toLowerCase() + name.substring(1), list);
                                }
                            }
                        }
                    } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    }
                }
            }

            return null;
        }

        @Override
        protected boolean isCandidateComponent(
                AnnotatedBeanDefinition beanDefinition) {
            return true;
        }

        @Override
        protected boolean checkCandidate(String beanName,
                                         BeanDefinition beanDefinition) throws IllegalStateException {
            if (super.checkCandidate(beanName, beanDefinition)) {
                return true;
            } else {
                logger.warn("Skipping MapperFactoryBean with name '" + beanName
                        + "' and '" + beanDefinition.getBeanClassName()
                        + "' mapperInterface"
                        + ". Bean already defined with the same name!");
                return false;
            }
        }
    }
}
