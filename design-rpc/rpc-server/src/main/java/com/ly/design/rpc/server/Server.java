package com.ly.design.rpc.server;

import com.ly.design.rpc.api.NameService;
import com.ly.design.rpc.api.RpcEndPoint;
import com.ly.design.rpc.api.spi.ServiceSupport;
import com.ly.design.rpc.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.File;
import java.net.URI;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    public static void main(String [] args) throws Exception {
        String serviceName = HelloService.class.getCanonicalName();
        File tmpDirFile = new File(System.getProperty("java.io.tmpdir"));
        File file = new File(tmpDirFile, "simple_rpc_name_service.data");
        HelloService helloService = new HelloServiceImpl();
        logger.info("创建并启动RpcEndPoint...");
        try(RpcEndPoint rpcEndPoint = ServiceSupport.load(RpcEndPoint.class);
            Closeable ignored = rpcEndPoint.startServer()) {
            NameService nameService = rpcEndPoint.getNameService(file.toURI());
            assert nameService != null;
            logger.info("向RpcEndPoint注册{}服务...", serviceName);
            URI uri = rpcEndPoint.addServiceProvider(helloService, HelloService.class);
            logger.info("服务名: {}, 向NameService注册...", serviceName);
            nameService.registerService(serviceName, uri);
            logger.info("开始提供服务，按任何键退出.");
            //noinspection ResultOfMethodCallIgnored
            System.in.read();
            logger.info("Bye!");
        }
    }
}
