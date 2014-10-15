package com.vooft.pilot;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Created with IntelliJ IDEA.
 * User: vooft
 * Date: 08.05.13
 * Time: 1:50
 * To change this template use File | Settings | File Templates.
 */
public class Launcher {
    /** run under root context */
    private static String contextPath = "/";
    /** location where resources should be provided from for VAADIN resources */
    private static String resourceBase = "src/main/webapp";
    /** port to listen on */
    private static int httpPort = 8081;

    private static String[] __dftConfigurationClasses =
            {
                    "org.eclipse.jetty.webapp.WebInfConfiguration",
                    "org.eclipse.jetty.webapp.WebXmlConfiguration",
                    "org.eclipse.jetty.webapp.MetaInfConfiguration",
                    "org.eclipse.jetty.webapp.FragmentConfiguration",
                    "org.eclipse.jetty.plus.webapp.EnvConfiguration",
                    "org.eclipse.jetty.webapp.JettyWebXmlConfiguration"
            } ;

    /**
     * Start the server, and keep waiting.
     */
    public static void main(String[] args) throws Exception {
        System.setProperty("java.naming.factory.url","org.eclipse.jetty.jndi");
        System.setProperty("java.naming.factory.initial","org.eclipse.jetty.jndi.InitialContextFactory");

        //InitialContext ctx = new InitialContext();

        Server server = new Server(httpPort);
        WebAppContext webapp = new WebAppContext();
        webapp.setClassLoader(Thread.currentThread().getContextClassLoader());
        //webapp.setConfigurationClasses(__dftConfigurationClasses);

        webapp.setServer(server);
        webapp.setDescriptor(resourceBase + "/WEB-INF/web.xml");
        webapp.setContextPath(contextPath);
        webapp.setResourceBase(resourceBase);
        webapp.setClassLoader(Thread.currentThread().getContextClassLoader());

        server.setHandler(webapp);
        server.start();

        server.join();
    }

}