package com.project.xghk416;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeGenerator {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        /**
         * 全局配置
         */
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
//        支持ac
        gc.setActiveRecord(true);
//        生成路径
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("XGHK416");
//        文件覆盖
        gc.setFileOverride(true);
//        主键自增
        gc.setIdType(IdType.AUTO);
//        Service和Impl层名字自定义
//        gc.setServiceName("%sService");
//        gc.setServiceImplName("%sImplService");
        gc.setEntityName("%sPo");
        gc.setMapperName("%sDao");
//        基本resultmap和基本列
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setOpen(false);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        /**
         * 数据源配置
         */
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setUrl("jdbc:mysql://39.106.228.42:3306/Myself?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("Myself");
        dsc.setPassword("hongzi123");
        mpg.setDataSource(dsc);

        /**
         * 包配置
         */
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(null);
//        设置包名
        pc.setParent("com.project.xghk416");
        pc.setMapper("pojo.dao");
        pc.setEntity("pojo.po");
        pc.setXml("pojo.dao.xml");
//        pc.setService("service");
//        pc.setServiceImpl("service.serviceImpl");
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        /**
        * 配置模板
        */
        TemplateConfig templateConfig = new TemplateConfig();


        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        //不生成如下类型模板
        templateConfig.setController(null);
//        templateConfig.setEntity(null);
//        templateConfig.setMapper(null);

        mpg.setTemplate(templateConfig);

        /**
         * 自定义
         */
        // 如果模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
         String templatePath = "/templates/mapper.xml.vm";
         String mapperXmlPath = "/templates/mapper.xml.vm";
         String mapperJavaPath = "/templates/mapper.java.vm";
         String entityPath= "/templates/entity.java.vm";
        // 自定义输出配置
//        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
//        focList.add(new FileOutConfig(entityPath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义Entity文件名跟生成路径
//                return projectPath + "/src/main/java/com/project/xghk416/pojo/po/"+ tableInfo.getEntityName() + StringPool.DOT_JAVA;
//            }
//        });
//        focList.add(new FileOutConfig(mapperJavaPath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义Dao类文件名和生成路径
//                tableInfo.toString();
//                return projectPath+"/src/main/java/com/project/xghk416/pojo/dao/" +tableInfo.getEntityName()+StringPool.DOT_JAVA;
//            }
//        });
//        InjectionConfig cfg = new InjectionConfig() {
//            @Override
//            public void initMap() {
//                // to do nothing
//            }
//        };
//
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);



        /**
         * 策略配置
         */
        StrategyConfig strategy = new StrategyConfig();
//        全局大写
        strategy.setCapitalMode(true);
//        数据库下划线转驼峰
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        表前缀
//        strategy.setTablePrefix("bili");
//        strategy.setSuperEntityClass("com.baomidou.mybatisplus.extension.activerecord.Model");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
//        // 公共父类
//        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
//        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
//        生成表名字
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());


        /**
         * 启动
         */
        mpg.execute();
    }

}
