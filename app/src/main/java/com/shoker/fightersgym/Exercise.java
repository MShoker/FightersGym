package com.shoker.fightersgym;

public class Exercise {
    private String mName;
    private String mSort;
    private String mRID;
    private String mEquipment;
    private int mId;
    private boolean selected = false;

    public Exercise(){

    }



    public Exercise(int id, String name, String sort , String rid, String equipment){
        mName = name;
        mSort = sort;
        mRID = rid;
        mId=id;
        mEquipment = equipment;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setSort(String mSort) {
        this.mSort = mSort;
    }

    public void setRID(String mRID) {
        this.mRID = mRID;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public void setEquipment(String mEquipment) {
        this.mEquipment = mEquipment;
    }



    public String getName() {
        return mName;
    }

    public String getSort() {
        return mSort;
    }

    public String getRID() {
        return mRID;
    }

    public int getId() {
        return mId;
    }

    public String getEquipment() {
        return mEquipment;
    }

}
