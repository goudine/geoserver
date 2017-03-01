package org.geogig.geoserver.rest;

import com.google.common.base.Preconditions;
import com.noelios.restlet.http.HttpRequest;
import org.geogig.geoserver.config.RepositoryInfo;
import org.geogig.geoserver.config.RepositoryManager;
import org.locationtech.geogig.rest.repository.CommandResource;
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

/**
 * Created by agoudine on 2017-02-09.
 */
public class ImportRepoCommandResource extends CommandResource{

    @Override
    protected String getCommandName() {
        return "importExistingRepo";
    }

    @Override
    protected Representation runCommand(Variant variant, Request request) {

        // get the repo URI
        URI pgURI = ImportRequestHandler.getURI(request);

        // save the repository
        saveRepository(pgURI);

        // grab new variant  [NOT SURE IF NECESSARY]
        //Variant newVariant = getPreferredVariant();

        // get the correct content type
        String contentType = ((HttpRequest)request).getHttpCall().getRequestHeaders().getValues("Content-Type");

        // create new MT object in order to set MediaType in Variant
        //MediaType requestType = new MediaType(contentType);
        //newVariant.setMediaType(requestType);
        MediaType requestType = new MediaType(contentType);
        variant.setMediaType(requestType);

        // get the options (metadata)
        Form options = getOptions();

        // set media type
        MediaType format = resolveFormat(options, variant);

        // create representation
//        geogig = getGeogig(request);
//        RestletContext ctx = new RestletContext(geogig.get(), request);
//        Representation representation = ctx.getRepresentation(format, (String) null);

        return null;
    }

    private RepositoryInfo saveRepository(URI pgURI) {

        // create a RepositoryInfo object
        RepositoryInfo repoInfo = new RepositoryInfo();

        // set the repo location from the URI
        repoInfo.setLocation(pgURI);

        // save the repo, this will set a UUID
        return RepositoryManager.get().save(repoInfo);
    }

}
