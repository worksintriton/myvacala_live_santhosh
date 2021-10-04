package com.triton.myvacala.responsepojo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VehicleTypeGetListResponse {




    /**
     * Status : Success
     * Message : Vehicledetails
     * Data : [{"_id":"5f02dc9ac9d5952f72f21757","Vehicle_Type":"Two Wheeler","updatedAt":"2020-07-06T08:11:06.967Z","createdAt":"2020-07-06T08:11:06.967Z","__v":0},{"_id":"5f02dcaac9d5952f72f21758","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-06T08:11:22.212Z","createdAt":"2020-07-06T08:11:22.212Z","__v":0}]
     * Code : 200
     */

    private String Status;
    private String Message;
    private int Code;
    private List<DataBean> Data;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * _id : 5f02dc9ac9d5952f72f21757
         * Vehicle_Type : Two Wheeler
         * updatedAt : 2020-07-06T08:11:06.967Z
         * createdAt : 2020-07-06T08:11:06.967Z
         * __v : 0
         */

        private String _id;
        private String Vehicle_Type;
        private String updatedAt;
        private String createdAt;
        private int __v;

        @NotNull
        @Override
        public String toString() {
            return Vehicle_Type;


        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getVehicle_Type() {
            return Vehicle_Type;
        }

        public void setVehicle_Type(String Vehicle_Type) {
            this.Vehicle_Type = Vehicle_Type;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }
    }
}
