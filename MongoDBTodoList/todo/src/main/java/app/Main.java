package app;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.message.GZipEncoder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.EncodingFilter;
import org.glassfish.jersey.servlet.ServletContainer;

public class Main {
    public static void main(String[] args) {

        // Init with session
        // https://stackoverflow.com/questions/37713926/what-does-no-security-do-in-jettys-servletcontexthandler
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.setContextPath("/");

        // Set the maximum size of a form post, to protect against DOS attacks from large forms.
        // -1 means no limitation
        servletContextHandler.setMaxFormContentSize(-1);

        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages("resources");

        // Enable GZip encoder
        EncodingFilter.enableFor(resourceConfig, GZipEncoder.class);


        ServletContainer servletContainer = new ServletContainer(resourceConfig);
        ServletHolder servletHolder = new ServletHolder(servletContainer);
        servletContextHandler.addServlet(servletHolder, "/*");

        Server server = new Server(8080);
        server.setHandler(servletContextHandler);
        server.dumpStdErr();

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

            try {
                server.stop();
            } catch (Exception ee) {
                ee.printStackTrace();
            }

            server.destroy();
            System.exit(1);
        }
    }
}