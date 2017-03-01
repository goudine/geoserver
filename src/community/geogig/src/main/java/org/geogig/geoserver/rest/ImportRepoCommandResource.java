package org.geogig.geoserver.rest;

import com.google.common.base.Preconditions;
import com.noelios.restlet.http.HttpRequest;
import org.geogig.geoserver.config.RepositoryInfo;
import org.geogig.geoserver.config.RepositoryManager;
import org.locationtech.geogig.rest.repository.CommandResource;
import org.locationtech.geogig.web.api.ParameterSet;
import org.locationtech.geogig.web.api.StreamResponse;
import org.locationtech.geogig.web.api.StreamWriterRepresentation;
import org.locationtech.geogig.web.api.WebAPICommand;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Status;
import org.restlet.resource.Representation;
import org.restlet.resource.Variant;

import java.io.File;
import java.net.URI;
import java.util.Map;

import static org.locationtech.geogig.web.api.RESTUtils.getGeogig;

public class ImportRepoCommandResource extends CommandResource{

    @Override
    protected String getCommandName() {
        return "importExistingRepo";
    }

    @Override
    protected WebAPICommand buildCommand(String commandName, ParameterSet options) {
        return new ImportExistingRepo(options);
    }
}
