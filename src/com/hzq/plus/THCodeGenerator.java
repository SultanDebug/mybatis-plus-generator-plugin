package com.hzq.plus;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Unit test for simple App.
 */
public class THCodeGenerator
{
//    static String projectPath = System.getProperty("user.dir");
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

    public static GlobalConfig getGc(String path,String author,String module){
        // 全局配置
        GlobalConfig gc = new GlobalConfig();

        gc.setOutputDir(path+module + "/src/main/java");

        //是否覆盖
        gc.setFileOverride(true);
        gc.setActiveRecord(false);// 开启 activeRecord 模式
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setAuthor(author);
        gc.setOpen(false);
        //格式化定义类名
        gc.setServiceName("%sService");
        gc.setEntityName("%sDomain");
        return gc;
    }

    public static DataSourceConfig getDsc(String ip , String user , String pass){
        // todo 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://10.66.2.23:3306/mall_moduledb?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUrl("jdbc:mysql://"+ip+"?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true");
        dsc.setUsername(user);
        dsc.setPassword(pass);
//        dsc.setUsername("mall_moduledb");
//        dsc.setPassword("H639yv8XBRUKnh1f");
        return dsc;
    }

    public static PackageConfig getPkg(String main,String sec){
        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
        // 第二级包路径
        pc.setModuleName(sec);
        // 第一级包路径
        pc.setParent(main);
        return pc;
    }

    public static InjectionConfig getCfg(String path,String module){
        // 自定义配置mapper.xml输出路径
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // 设置ftl自定义参数
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper_th.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件路径及名称
                return path +module+ "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    public static TemplateConfig getTmp(){
        //模板定义
        TemplateConfig templateConfig = new TemplateConfig();

        //全局设为null表示不生成
        templateConfig.setEntity("templates/entity_th.java") // entity模板采用自定义模板
                .setMapper("templates/mapper_th.java")// mapper模板采用自定义模板
                .setXml(null) // 在包路径内不生成xml文件
                .setService("templates/service_th.java") // service模板采用自定义模板
                .setServiceImpl("templates/serviceImpl_th.java") // serviceImpl模板采用自定义模板
                .setController("templates/controller_th.java"); // controller模板采用自定义模板
        return templateConfig;
    }

    public static StrategyConfig getStr(String name,String tabName){
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setRestControllerStyle(true);
//        strategy.setSuperEntityClass("com.lingzhi.saas.mall.store.domain.ModuleBaseDomain");
        strategy.setEntityLombokModel(true);
        strategy.setSuperMapperClass("com.lingzhi.dubhe.mapper.BaseMapper");
        strategy.setSuperServiceClass("com.lingzhi.dubhe.service.BaseService");
        strategy.setSuperServiceImplClass("com.lingzhi.dubhe.service.BaseServiceImpl");
        strategy.setSuperControllerClass("com.lingzhi.dubhe.controller.BaseController");
//        strategy.setInclude(scanner("表名"));

        //指定表名
//        strategy.setInclude(scanner("输入表名（如：module_config）"));
        strategy.setInclude(tabName);
        //id继承
//        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(name + "_");
        return strategy;
    }
    /**
     * 入口
     * 修改todo数据源部分
     */
    public static void init(String path ,String ip,String user,String pass,String author,String main,String sec,String table,String module) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //全局配置
        GlobalConfig gc = getGc(path,author,module);
        mpg.setGlobalConfig(gc);

        //获取数据源
        DataSourceConfig dsc = getDsc(ip,user,pass);
        mpg.setDataSource(dsc);

        //获取包配置
        PackageConfig pkg = getPkg(main,sec);
        mpg.setPackageInfo(pkg);

        //自定义（特殊输出路径等）
        InjectionConfig cfg = getCfg(path,module);
        mpg.setCfg(cfg);


        //模板配置
        TemplateConfig templateConfig = getTmp();
        mpg.setTemplate(templateConfig);

        //策略配置（父包配置等）
        StrategyConfig str = getStr(pkg.getModuleName(),table);
        mpg.setStrategy(str);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        //执行
        mpg.execute();
    }


}
