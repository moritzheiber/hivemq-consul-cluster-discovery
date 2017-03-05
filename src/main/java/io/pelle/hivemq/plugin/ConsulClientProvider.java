package io.pelle.hivemq.plugin;

import com.google.common.net.HostAndPort;
import com.hivemq.spi.exceptions.UnrecoverableException;
import com.orbitz.consul.Consul;
import com.orbitz.consul.ConsulException;
import io.pelle.hivemq.plugin.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;

public class ConsulClientProvider implements Provider<Consul> {

    private static final Logger log = LoggerFactory.getLogger(ConsulClientProvider.class);

    private final Configuration configuration;

    @Inject
    public ConsulClientProvider(final Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Consul get() {

        String consulHostname = configuration.getConsulHostname();
        int consulPort = configuration.getConsulPort();
        log.info("connecting to consul {}:{}", consulHostname, consulPort);

        try {
            return Consul.builder().withHostAndPort(HostAndPort.fromParts(consulHostname, consulPort)).build();
        } catch (ConsulException e) {
            log.error("unable to connect to consul", e);
            throw new UnrecoverableException();
        }
    }
}