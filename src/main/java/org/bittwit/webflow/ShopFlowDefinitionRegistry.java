package org.bittwit.webflow;

import java.io.File;
import java.io.IOException;

import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.config.FlowDefinitionResourceFactory;
import org.springframework.webflow.definition.FlowDefinition;
import org.springframework.webflow.definition.registry.FlowDefinitionConstructionException;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistryImpl;
import org.springframework.webflow.definition.registry.NoSuchFlowDefinitionException;
import org.springframework.webflow.engine.builder.DefaultFlowHolder;
import org.springframework.webflow.engine.builder.FlowAssembler;
import org.springframework.webflow.engine.builder.FlowBuilder;
import org.springframework.webflow.engine.builder.FlowBuilderContext;
import org.springframework.webflow.engine.builder.model.FlowModelFlowBuilder;
import org.springframework.webflow.engine.builder.support.FlowBuilderContextImpl;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.engine.model.builder.DefaultFlowModelHolder;
import org.springframework.webflow.engine.model.builder.xml.XmlFlowModelBuilder;
import org.springframework.webflow.engine.model.registry.FlowModelHolder;

/**
 * 
 */
public class ShopFlowDefinitionRegistry extends FlowDefinitionRegistryImpl {

    /**
     * The definition registry produced by this factory bean.
     */
    private FlowBuilderServices flowBuilderServices;
    private FlowDefinition flowDefinition;
    private String contextRelativeFlowPath;

    public ShopFlowDefinitionRegistry() {
        super();
    }

    /*
     * Always returns the flow configured in contextRelativeFlowPath XML
     */
    public FlowDefinition getFlowDefinition(String id) throws NoSuchFlowDefinitionException,
            FlowDefinitionConstructionException {

        if (this.flowDefinition == null) {
            try {
                initDefaultFlowDefinition();
            } catch (IOException e) {
                e.printStackTrace();
                throw new FlowDefinitionConstructionException(
                        this.contextRelativeFlowPath != null ? this.contextRelativeFlowPath : "", e);
            }
        }

        return this.flowDefinition;
    }

    /*
     * Ignores default behaviour. It just return false, because whenever it is used to retrieve a flow, it will return
     * the default one, no matter what flow is requested. {@see
     * org.springframework.webflow.mvc.servlet.FlowHandlerMapping.getHandlerInternal(HttpServletRequest)}.
     */
    public boolean containsFlowDefinition(String flowId) {
        return false;
    }

    private void initDefaultFlowDefinition() throws IOException {
        if (this.flowDefinition != null) {
            return;
        }
        if (this.contextRelativeFlowPath == null) {
            throw new RuntimeException("'flowPath' field not initialized. Initialize it in XML config.");
        }
        System.out.println("Building default flow definition: " + this.contextRelativeFlowPath);
        FlowDefinitionResourceFactory resourceFactory = new FlowDefinitionResourceFactory();

        File flowAbsolutePath = this.flowBuilderServices.getApplicationContext()
                .getResource(this.contextRelativeFlowPath).getFile();
        FlowDefinitionResource flowResource = resourceFactory.createFileResource(flowAbsolutePath.getAbsolutePath());
        XmlFlowModelBuilder flowModelBuilder = new XmlFlowModelBuilder(flowResource.getPath());
        FlowModelHolder modelHolder = new DefaultFlowModelHolder(flowModelBuilder);
        FlowBuilder flowBuilder = new FlowModelFlowBuilder(modelHolder);
        FlowBuilderContext builderContext = new FlowBuilderContextImpl(flowResource.getId(),
                flowResource.getAttributes(), this, this.flowBuilderServices);
        FlowAssembler assembler = new FlowAssembler(flowBuilder, builderContext);

        this.flowDefinition = new DefaultFlowHolder(assembler).getFlowDefinition();
    }

    public void setFlowBuilderServices(FlowBuilderServices flowBuilderServices) {
        this.flowBuilderServices = flowBuilderServices;
    }

    public void setContextRelativeFlowPath(String contextRelativeFlowPath) {
        this.contextRelativeFlowPath = contextRelativeFlowPath;
    }
}
