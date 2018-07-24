package com.gofar.wanandroid.c.tree.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author lcf
 * @date 20/7/2018 下午 5:52
 * @since 1.0
 */
public class TreeEntity implements Parcelable{

    /**
     * children : []
     * courseId : 13
     * id : 60
     * name : Android Studio相关
     * order : 1000
     * parentChapterId : 150
     * visible : 1
     */

    private int courseId;
    private int id;
    private String name;
    private int order;
    private int parentChapterId;
    private int visible;
    private List<TreeEntity> children;

    public TreeEntity() {
    }

    protected TreeEntity(Parcel in) {
        courseId = in.readInt();
        id = in.readInt();
        name = in.readString();
        order = in.readInt();
        parentChapterId = in.readInt();
        visible = in.readInt();
        children = in.createTypedArrayList(TreeEntity.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(courseId);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(order);
        dest.writeInt(parentChapterId);
        dest.writeInt(visible);
        dest.writeTypedList(children);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TreeEntity> CREATOR = new Creator<TreeEntity>() {
        @Override
        public TreeEntity createFromParcel(Parcel in) {
            return new TreeEntity(in);
        }

        @Override
        public TreeEntity[] newArray(int size) {
            return new TreeEntity[size];
        }
    };

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(int parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public List<TreeEntity> getChildren() {
        return children;
    }

    public void setChildren(List<TreeEntity> children) {
        this.children = children;
    }
}
