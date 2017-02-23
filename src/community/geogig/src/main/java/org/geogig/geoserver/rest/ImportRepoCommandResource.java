package org.geogig.geoserver.rest;

import org.geogig.geoserver.config.RepositoryInfo;
import org.geogig.geoserver.config.RepositoryManager;
import org.locationtech.geogig.rest.repository.CommandResource;
import org.restlet.data.Request;
import org.restlet.data.Status;
import org.restlet.resource.Representation;
import org.restlet.resource.Variant;

import java.io.File;
import java.net.URI;
import java.util.Map;

/**
 * Created by agoudine on 2017-02-09.
 */
public class ImportRepoCommandResource extends CommandResource{

    // This is the `InitCommandResource` code, needs to be changed
    @Override
    protected String getCommandName() {
        return "importExistingRepo";
    }

    @Override
    protected Representation runCommand(Variant variant, Request request) {

        Representation representation = super.runCommand(variant, request);
        Map<String, String> requestParameters = ImportRequestHandler.getRequestParameters(request);
        saveRepository(requestParameters);

        //InitRequestHandler requestHandler = new InitRequestHandler();

        return representation;
    }

    private RepositoryInfo saveRepository(Map<String, String> requestParameters) {
        // repo was just created, need to register it with an ID in the manager
        // create a RepositoryInfo object
        RepositoryInfo repoInfo = new RepositoryInfo();
        URI location = geogig.get().getLocation().normalize();

        // do something that takes request and gives us a URI
        // set the URI on the repo info --> repoInfo.setLocation(URI)
        URI pgURI;
        if (requestParameters.containsKey(ImportRequestHandler.DB_NAME)) {
            // this is a PG repo
            pgURI = URI.create("postgresql://"+"see the actual URI"+ImportRequestHandler.DB_HOST);
        } else {
            pgURI = URI.create("other URI");
        }

        if ("file".equals(location.getScheme())) {
            // need the parent
            File parentDir = new File(location).getParentFile();
            location = parentDir.toURI().normalize();
        }
        // set the URI
        repoInfo.setLocation(pgURI);
        // save the repo, this will set a UUID
        return RepositoryManager.get().save(repoInfo);
    }

}
