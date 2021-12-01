package com.ly.design.rpc.netty.nameservice;

import com.ly.design.rpc.api.NameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Collections;

/**
 * LocalFile store the nameservice
 */
public class LocalFileNameService implements NameService {
    private static final Logger logger = LoggerFactory.getLogger(LocalFileNameService.class);
    private static final Collection<String> schemes = Collections.singleton("file");
    private File file;

    @Override
    public Collection<String> supportedSchemes() {
        return schemes;
    }

    @Override
    public void connect(URI nameServiceUri) {

    }

    @Override
    public void registerService(String serviceName, URI uri) throws IOException {

    }

    @Override
    public URI lookupService(String serviceName) throws IOException {
        return null;
    }
}
