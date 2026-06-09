package endpoints;

public class routes {
	
	 public static final String GET_COLLECTIONS =
	            "/collections";

	    public static final String GET_OBJECTS_IN_COLLECTION =
	            "/collections/{collectionName}/objects";
	 
	    public static final String CREATE_OBJECT_IN_COLLECTION=
	    		"collections/{collectionName}/objects";
	    public static final String GET_OBJECT_IN_COLLECTION_BY_ID=
	    		"/collections/{collectionName}/objects/{id}";
	    public static final String UPDATE_OBJECT_IN_COLLECTION=
	    		"/collections/{collectionName}/objects/{id}";
	    public static final String DELETE_OBJECT_BY_ID=
	    		"/collections/{collectionName}/objects/{id}";

}
