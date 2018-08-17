package resources;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/db")
public class Database
{
    private MongoClient client;
    private MongoDatabase database;
    private String uri = "mongodb://quocbao:a123456@ds121382.mlab.com:21382/restapi";

    public Database()
    {
        MongoClientURI uri = new MongoClientURI(this.uri);

        this.client = new MongoClient(uri);
        this.database = client.getDatabase("restapi");

        System.out.println("Connected to the database successfully");
    }

    Block<Document> printBlock = document -> System.out.println(document.toJson());

    @GET
    @Path("/get/collection/{message}")
    public Response getMsg(@PathParam("message") String collection)
    {
        MongoCollection<Document> collectionHandler = database.getCollection(collection);
        FindIterable<Document> documents = collectionHandler.find();

        StringBuilder output = new StringBuilder();
        for (Document document : documents)
        {
            output.append(document.toJson()).append("<br/>");
        }

        return Response.status(200).entity(output.toString()).build();
    }

    @GET
    @Path("/get/collections")
    public Response getMsg()
    {
        // Creating Credentials
        //System.out.println("Connected to the database successfully");

        // Accessing the database

        MongoIterable<String> collections = this.database.listCollectionNames();

        StringBuilder output = new StringBuilder();
        for (String collection : collections)
        {
            output.append(collection).append("<br/>");
        }

        return Response.status(200).entity(output.toString()).build();    }
}