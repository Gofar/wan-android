package com.gofar.component.basiclib.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lcf
 * @date 2018/6/6 11:01
 * @since 1.0
 */
public class BaseListResponse<T> {

    @SerializedName("errorCode")
    private int errorCode;
    @SerializedName("errorMsg")
    private String errorMsg;
    @SerializedName("data")
    private DataWrapperEntity<T> data;

    public DataWrapperEntity<T> getData() {
        return data;
    }

    public void setData(DataWrapperEntity<T> data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * 是否请求成功
     *
     * @return true:成功,false:失败
     */
    public boolean isSuccess() {
        return errorCode == BaseResponse.SUCCESS_CODE;
    }

    /**
     * 获取列表数据
     *
     * @return 列表数据
     */
    public List<T> getDataList() {
        if (data != null) {
            List<T> dataList = data.getDataList();
            if (dataList != null && !dataList.isEmpty()) {
                return dataList;
            }
        }
        return null;
    }

    public static class DataWrapperEntity<T> {
        private int curPage;
        private int offset;
        private boolean over;
        private int pageCount;
        private int size;
        private int total;
        @SerializedName("datas")
        private List<T> dataList;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public boolean isOver() {
            return over;
        }

        public void setOver(boolean over) {
            this.over = over;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<T> getDataList() {
            return dataList;
        }

        public void setDataList(List<T> dataList) {
            this.dataList = dataList;
        }
    }
}
