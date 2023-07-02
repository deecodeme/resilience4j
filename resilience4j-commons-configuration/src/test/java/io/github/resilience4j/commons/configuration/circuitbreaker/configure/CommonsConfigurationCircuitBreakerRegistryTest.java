package io.github.resilience4j.commons.configuration.circuitbreaker.configure;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.common.CompositeCustomizer;
import io.github.resilience4j.commons.configuration.util.Constants;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class CommonsConfigurationCircuitBreakerRegistryTest {

    @Test
    public void testCircuitBreakerRegistryFromCommonsConfiguration() throws ConfigurationException {
        FileBasedConfigurationBuilder<PropertiesConfiguration> builder = new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                .configure(new Parameters()
                        .fileBased()
                        .setListDelimiterHandler(new DefaultListDelimiterHandler(Constants.LIST_DELIMITER))
                        .setFileName(Constants.RESILIENCE_CONFIG_PROPERTIES_FILE_NAME));
        Configuration config = builder.getConfiguration();

        CircuitBreakerRegistry circuitBreakerRegistry = CommonsConfigurationCircuitBreakerRegistry.of(config, new CompositeCustomizer<>(List.of()));

        Assertions.assertThat(circuitBreakerRegistry.circuitBreaker(Constants.BACKEND_A).getName()).isEqualTo(Constants.BACKEND_A);
        Assertions.assertThat(circuitBreakerRegistry.circuitBreaker(Constants.BACKEND_B).getName()).isEqualTo(Constants.BACKEND_B);
    }
}