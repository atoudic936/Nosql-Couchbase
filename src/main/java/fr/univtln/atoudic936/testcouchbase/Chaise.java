package fr.univtln.atoudic936.testcouchbase;

import com.couchbase.client.java.document.json.JsonObject;

import java.io.Serializable;

public class Chaise implements Serializable{

    private long ID;

    private String Material;

    private boolean BonEtat;


    public Chaise() {
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        this.Material = material;
    }

    public boolean isBonEtat() {
        return BonEtat;
    }

    public void setBonEtat(boolean bonEtat) {
        this.BonEtat = bonEtat;
    }

    public JsonObject toJson(){
        JsonObject object = JsonObject.create();
        return JsonObject.create().put("ID", getID()).put("Material", getMaterial()).put("BonEtat", isBonEtat());
    }
}
