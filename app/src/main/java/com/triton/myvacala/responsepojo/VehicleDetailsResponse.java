package com.triton.myvacala.responsepojo;

import java.util.List;

public class VehicleDetailsResponse {


    /**
     * Status : Success
     * Message : Vehicledetails
     * Data : [{"Fuel_Type":[{"_id":"5f1e692a9f12a81b3dd70890","Fuel_Type":"Petrol","Background_Color":"#802A2A","updatedAt":"2020-07-27T05:42:02.196Z","createdAt":"2020-07-27T05:42:02.196Z","__v":0},{"_id":"5f1e69aa9f12a81b3dd70891","Fuel_Type":"Diesel","Background_Color":"#008000","updatedAt":"2020-07-27T05:44:10.895Z","createdAt":"2020-07-27T05:44:10.895Z","__v":0}],"Vehicle_Model":[{"_id":"5f1af089de7bf45b602f8bb3","VehicleModel_Name":"Standard","VehicleModel_Image":"http://3.101.31.129:3000/api/uploads/695cd4ea-9cbe-434d-afb8-b0a102314e8b.png","Vehicle_Type":"Two Wheeler","updatedAt":"2020-07-24T14:30:33.734Z","createdAt":"2020-07-24T14:30:33.734Z","__v":0}],"_id":"5f1e70539f12a81b3dd708a1","Vehicle_Brand_id":{"_id":"5f1e6ba19f12a81b3dd70893","Vehicle_Type_id":"5f0c0d092f857d66950cf260","Vehicle_Brand_Name":"Bajaj","updatedAt":"2020-07-27T05:52:33.252Z","createdAt":"2020-07-27T05:52:33.252Z","__v":0},"Vehicle_Type":{"_id":"5f0c0d092f857d66950cf260","Vehicle_Type":"Two Wheeler","updatedAt":"2020-07-13T07:28:09.903Z","createdAt":"2020-07-13T07:28:09.903Z","__v":0},"Vehicle_Image":"http://3.101.31.129:3000/api/uploads/bajaj-pulsar.png","Vehicle_Name":"Pulsar","Vehicle_CC":"1200","Popular_Vehicle":false,"mfg_year_start":2010,"mfg_year_end":2020,"updatedAt":"2020-07-27T08:26:06.096Z","createdAt":"2020-07-27T06:12:35.339Z","__v":0}]
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
         * Fuel_Type : [{"_id":"5f1e692a9f12a81b3dd70890","Fuel_Type":"Petrol","Background_Color":"#802A2A","updatedAt":"2020-07-27T05:42:02.196Z","createdAt":"2020-07-27T05:42:02.196Z","__v":0},{"_id":"5f1e69aa9f12a81b3dd70891","Fuel_Type":"Diesel","Background_Color":"#008000","updatedAt":"2020-07-27T05:44:10.895Z","createdAt":"2020-07-27T05:44:10.895Z","__v":0}]
         * Vehicle_Model : [{"_id":"5f1af089de7bf45b602f8bb3","VehicleModel_Name":"Standard","VehicleModel_Image":"http://3.101.31.129:3000/api/uploads/695cd4ea-9cbe-434d-afb8-b0a102314e8b.png","Vehicle_Type":"Two Wheeler","updatedAt":"2020-07-24T14:30:33.734Z","createdAt":"2020-07-24T14:30:33.734Z","__v":0}]
         * _id : 5f1e70539f12a81b3dd708a1
         * Vehicle_Brand_id : {"_id":"5f1e6ba19f12a81b3dd70893","Vehicle_Type_id":"5f0c0d092f857d66950cf260","Vehicle_Brand_Name":"Bajaj","updatedAt":"2020-07-27T05:52:33.252Z","createdAt":"2020-07-27T05:52:33.252Z","__v":0}
         * Vehicle_Type : {"_id":"5f0c0d092f857d66950cf260","Vehicle_Type":"Two Wheeler","updatedAt":"2020-07-13T07:28:09.903Z","createdAt":"2020-07-13T07:28:09.903Z","__v":0}
         * Vehicle_Image : http://3.101.31.129:3000/api/uploads/bajaj-pulsar.png
         * Vehicle_Name : Pulsar
         * Vehicle_CC : 1200
         * Popular_Vehicle : false
         * mfg_year_start : 2010
         * mfg_year_end : 2020
         * updatedAt : 2020-07-27T08:26:06.096Z
         * createdAt : 2020-07-27T06:12:35.339Z
         * __v : 0
         */

        private String _id;
        private VehicleBrandIdBean Vehicle_Brand_id;
        private VehicleTypeBean Vehicle_Type;
        private String Vehicle_Image;
        private String Vehicle_Name;
        private String Vehicle_CC;
        private boolean Popular_Vehicle;
        private int mfg_year_start;
        private int mfg_year_end;
        private String updatedAt;
        private String createdAt;
        private int __v;
        private List<FuelTypeBean> Fuel_Type;
        private List<VehicleModelBean> Vehicle_Model;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public VehicleBrandIdBean getVehicle_Brand_id() {
            return Vehicle_Brand_id;
        }

        public void setVehicle_Brand_id(VehicleBrandIdBean Vehicle_Brand_id) {
            this.Vehicle_Brand_id = Vehicle_Brand_id;
        }

        public VehicleTypeBean getVehicle_Type() {
            return Vehicle_Type;
        }

        public void setVehicle_Type(VehicleTypeBean Vehicle_Type) {
            this.Vehicle_Type = Vehicle_Type;
        }

        public String getVehicle_Image() {
            return Vehicle_Image;
        }

        public void setVehicle_Image(String Vehicle_Image) {
            this.Vehicle_Image = Vehicle_Image;
        }

        public String getVehicle_Name() {
            return Vehicle_Name;
        }

        public void setVehicle_Name(String Vehicle_Name) {
            this.Vehicle_Name = Vehicle_Name;
        }

        public String getVehicle_CC() {
            return Vehicle_CC;
        }

        public void setVehicle_CC(String Vehicle_CC) {
            this.Vehicle_CC = Vehicle_CC;
        }

        public boolean isPopular_Vehicle() {
            return Popular_Vehicle;
        }

        public void setPopular_Vehicle(boolean Popular_Vehicle) {
            this.Popular_Vehicle = Popular_Vehicle;
        }

        public int getMfg_year_start() {
            return mfg_year_start;
        }

        public void setMfg_year_start(int mfg_year_start) {
            this.mfg_year_start = mfg_year_start;
        }

        public int getMfg_year_end() {
            return mfg_year_end;
        }

        public void setMfg_year_end(int mfg_year_end) {
            this.mfg_year_end = mfg_year_end;
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

        public List<FuelTypeBean> getFuel_Type() {
            return Fuel_Type;
        }

        public void setFuel_Type(List<FuelTypeBean> Fuel_Type) {
            this.Fuel_Type = Fuel_Type;
        }

        public List<VehicleModelBean> getVehicle_Model() {
            return Vehicle_Model;
        }

        public void setVehicle_Model(List<VehicleModelBean> Vehicle_Model) {
            this.Vehicle_Model = Vehicle_Model;
        }

        public static class VehicleBrandIdBean {
            /**
             * _id : 5f1e6ba19f12a81b3dd70893
             * Vehicle_Type_id : 5f0c0d092f857d66950cf260
             * Vehicle_Brand_Name : Bajaj
             * updatedAt : 2020-07-27T05:52:33.252Z
             * createdAt : 2020-07-27T05:52:33.252Z
             * __v : 0
             */

            private String _id;
            private String Vehicle_Type_id;
            private String Vehicle_Brand_Name;
            private String updatedAt;
            private String createdAt;
            private int __v;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getVehicle_Type_id() {
                return Vehicle_Type_id;
            }

            public void setVehicle_Type_id(String Vehicle_Type_id) {
                this.Vehicle_Type_id = Vehicle_Type_id;
            }

            public String getVehicle_Brand_Name() {
                return Vehicle_Brand_Name;
            }

            public void setVehicle_Brand_Name(String Vehicle_Brand_Name) {
                this.Vehicle_Brand_Name = Vehicle_Brand_Name;
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

        public static class VehicleTypeBean {
            /**
             * _id : 5f0c0d092f857d66950cf260
             * Vehicle_Type : Two Wheeler
             * updatedAt : 2020-07-13T07:28:09.903Z
             * createdAt : 2020-07-13T07:28:09.903Z
             * __v : 0
             */

            private String _id;
            private String Vehicle_Type;
            private String updatedAt;
            private String createdAt;
            private int __v;

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

        public static class FuelTypeBean {
            /**
             * _id : 5f1e692a9f12a81b3dd70890
             * Fuel_Type : Petrol
             * Background_Color : #802A2A
             * updatedAt : 2020-07-27T05:42:02.196Z
             * createdAt : 2020-07-27T05:42:02.196Z
             * __v : 0
             */

            private String _id;
            private String Fuel_Type;
            private String Background_Color;
            private String updatedAt;
            private String createdAt;
            private int __v;

            private boolean isSelected ;
            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }



            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getFuel_Type() {
                return Fuel_Type;
            }

            public void setFuel_Type(String Fuel_Type) {
                this.Fuel_Type = Fuel_Type;
            }

            public String getBackground_Color() {
                return Background_Color;
            }

            public void setBackground_Color(String Background_Color) {
                this.Background_Color = Background_Color;
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

        public static class VehicleModelBean {
            /**
             * _id : 5f1af089de7bf45b602f8bb3
             * VehicleModel_Name : Standard
             * VehicleModel_Image : http://3.101.31.129:3000/api/uploads/695cd4ea-9cbe-434d-afb8-b0a102314e8b.png
             * Vehicle_Type : Two Wheeler
             * updatedAt : 2020-07-24T14:30:33.734Z
             * createdAt : 2020-07-24T14:30:33.734Z
             * __v : 0
             */

            private String _id;
            private String VehicleModel_Name;
            private String VehicleModel_Image;
            private String Vehicle_Type;
            private String updatedAt;
            private String createdAt;
            private int __v;
            private boolean isSelected ;
            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }


            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getVehicleModel_Name() {
                return VehicleModel_Name;
            }

            public void setVehicleModel_Name(String VehicleModel_Name) {
                this.VehicleModel_Name = VehicleModel_Name;
            }

            public String getVehicleModel_Image() {
                return VehicleModel_Image;
            }

            public void setVehicleModel_Image(String VehicleModel_Image) {
                this.VehicleModel_Image = VehicleModel_Image;
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
}
