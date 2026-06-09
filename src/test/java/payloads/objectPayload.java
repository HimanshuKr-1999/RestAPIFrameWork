package payloads;

import java.util.HashMap;

public class objectPayload {
	
	private String name;
	private HashMap<String ,Object> data ;
	

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }
}
