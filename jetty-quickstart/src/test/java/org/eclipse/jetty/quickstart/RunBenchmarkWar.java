package org.eclipse.jetty.quickstart;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.webapp.WebAppContext;

public class RunBenchmarkWar 
{
    private static final long __start=System.nanoTime();
    private static final Logger LOG = Log.getLogger(Server.class);
    
    public static final String[] __plusConfigurationClasses = new String[] 
    {
        org.eclipse.jetty.webapp.WebInfConfiguration.class.getCanonicalName(),
        org.eclipse.jetty.webapp.WebXmlConfiguration.class.getCanonicalName(),
        org.eclipse.jetty.webapp.MetaInfConfiguration.class.getCanonicalName(),
        org.eclipse.jetty.webapp.FragmentConfiguration.class.getCanonicalName(),
        org.eclipse.jetty.plus.webapp.EnvConfiguration.class.getCanonicalName(),
        org.eclipse.jetty.plus.webapp.PlusConfiguration.class.getCanonicalName(),
        org.eclipse.jetty.annotations.AnnotationConfiguration.class.getCanonicalName(),
        org.eclipse.jetty.webapp.JettyWebXmlConfiguration.class.getCanonicalName()
    };
    
    public static void main(String... args) throws Exception
    {   
        Server server = new Server(8080);
        
        // Setup JMX
        MBeanContainer mbContainer=new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
        server.addBean(mbContainer);

        WebAppContext webapp = new WebAppContext();
        webapp.setConfigurationClasses(__plusConfigurationClasses);
        webapp.setContextPath("/");
        webapp.setWar("src/test/resources/benchmark-java-webapp-1.0.war");

        server.setHandler(webapp);

        server.start();

        LOG.info("Started in {}ms",TimeUnit.NANOSECONDS.toMillis(System.nanoTime()-__start));
        server.join();
    }
}
