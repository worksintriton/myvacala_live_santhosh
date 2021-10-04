package com.triton.myvacala.responsepojo;

import java.util.List;

public class PopularVehicleListResponse {


    /**
     * Status : Success
     * Message : Vehicledetails
     * Data : [{"_id":"5f1a842467024c39a738e821","Vehicle_Type_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Brand_id":"5f1a842467024c39a738e821","Vehicle_Type_Name":"","Vehicle_Brand_Image":"","Vehicle_Brand_Name":"","Vehicle_Details":[{"Fuel_Type":[{"_id":"5f1e692a9f12a81b3dd70890","Fuel_Type":"Petrol","Background_Color":"#802A2A","updatedAt":"2020-07-27T05:42:02.196Z","createdAt":"2020-07-27T05:42:02.196Z","__v":0},{"_id":"5f1e69aa9f12a81b3dd70891","Fuel_Type":"Diesel","Background_Color":"#008000","updatedAt":"2020-07-27T05:44:10.895Z","createdAt":"2020-07-27T05:44:10.895Z","__v":0}],"Vehicle_Model":[{"_id":"5f1af058de7bf45b602f8bb1","VehicleModel_Name":"Hatchbact","VehicleModel_Image":"http://3.101.31.129:3000/api/uploads/63f26304-9f74-4132-8e28-da33dd4f9214.png","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-24T14:29:44.857Z","createdAt":"2020-07-24T14:29:44.857Z","__v":0}],"_id":"5f1e6a299f12a81b3dd70892","Vehicle_Brand_id":"5f1a842467024c39a738e821","Vehicle_Image":"http://3.101.31.129:3000/api/uploads/0d09b03d-4ea1-42f2-8a53-a622500e0f0a.jpg","Vehicle_Name":"AUDI A1","updatedAt":"2020-07-27T07:48:23.832Z","createdAt":"2020-07-27T05:46:17.517Z","Vehicle_CC":"","mfg_year_start":1934,"mfg_year_end":1951,"__v":0}]},{"_id":"5f1a842467024c39a738e821","Vehicle_Type_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Brand_id":"5f1a842467024c39a738e821","Vehicle_Type_Name":"","Vehicle_Brand_Image":"","Vehicle_Brand_Name":"","Vehicle_Details":[{"Fuel_Type":[{"_id":"5f1e692a9f12a81b3dd70890","Fuel_Type":"Petrol","Background_Color":"#802A2A","updatedAt":"2020-07-27T05:42:02.196Z","createdAt":"2020-07-27T05:42:02.196Z","__v":0},{"_id":"5f1e69aa9f12a81b3dd70891","Fuel_Type":"Diesel","Background_Color":"#008000","updatedAt":"2020-07-27T05:44:10.895Z","createdAt":"2020-07-27T05:44:10.895Z","__v":0}],"Vehicle_Model":[{"_id":"5f1af058de7bf45b602f8bb1","VehicleModel_Name":"Hatchbact","VehicleModel_Image":"http://3.101.31.129:3000/api/uploads/63f26304-9f74-4132-8e28-da33dd4f9214.png","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-24T14:29:44.857Z","createdAt":"2020-07-24T14:29:44.857Z","__v":0}],"_id":"5f1e79dfa5e13d1c28e45eba","Vehicle_Brand_id":"5f1a842467024c39a738e821","Vehicle_Image":"http://3.101.31.129:3000/api/uploads/6e609c81-3c24-4ac5-8826-76b98907d734.jpg","Vehicle_Name":"AUDI A2","updatedAt":"2020-07-27T07:47:48.394Z","createdAt":"2020-07-27T06:53:19.139Z","Vehicle_CC":"","mfg_year_start":1916,"mfg_year_end":1937,"__v":0}]}]
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
         * _id : 5f1a842467024c39a738e821
         * Vehicle_Type_id : 5f0c0cfc2f857d66950cf25f
         * Vehicle_Brand_id : 5f1a842467024c39a738e821
         * Vehicle_Type_Name :
         * Vehicle_Brand_Image :
         * Vehicle_Brand_Name :
         * Vehicle_Details : [{"Fuel_Type":[{"_id":"5f1e692a9f12a81b3dd70890","Fuel_Type":"Petrol","Background_Color":"#802A2A","updatedAt":"2020-07-27T05:42:02.196Z","createdAt":"2020-07-27T05:42:02.196Z","__v":0},{"_id":"5f1e69aa9f12a81b3dd70891","Fuel_Type":"Diesel","Background_Color":"#008000","updatedAt":"2020-07-27T05:44:10.895Z","createdAt":"2020-07-27T05:44:10.895Z","__v":0}],"Vehicle_Model":[{"_id":"5f1af058de7bf45b602f8bb1","VehicleModel_Name":"Hatchbact","VehicleModel_Image":"http://3.101.31.129:3000/api/uploads/63f26304-9f74-4132-8e28-da33dd4f9214.png","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-24T14:29:44.857Z","createdAt":"2020-07-24T14:29:44.857Z","__v":0}],"_id":"5f1e6a299f12a81b3dd70892","Vehicle_Brand_id":"5f1a842467024c39a738e821","Vehicle_Image":"http://3.101.31.129:3000/api/uploads/0d09b03d-4ea1-42f2-8a53-a622500e0f0a.jpg","Vehicle_Name":"AUDI A1","updatedAt":"2020-07-27T07:48:23.832Z","createdAt":"2020-07-27T05:46:17.517Z","Vehicle_CC":"","mfg_year_start":1934,"mfg_year_end":1951,"__v":0}]
         */

        private String _id;
        private String Vehicle_Type_id;
        private String Vehicle_Brand_id;
        private String Vehicle_Type_Name;
        private String Vehicle_Brand_Image;
        private String Vehicle_Brand_Name;
        private List<VehicleDetailsBean> Vehicle_Details;

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

        public String getVehicle_Brand_id() {
            return Vehicle_Brand_id;
        }

        public void setVehicle_Brand_id(String Vehicle_Brand_id) {
            this.Vehicle_Brand_id = Vehicle_Brand_id;
        }

        public String getVehicle_Type_Name() {
            return Vehicle_Type_Name;
        }

        public void setVehicle_Type_Name(String Vehicle_Type_Name) {
            this.Vehicle_Type_Name = Vehicle_Type_Name;
        }

        public String getVehicle_Brand_Image() {
            return Vehicle_Brand_Image;
        }

        public void setVehicle_Brand_Image(String Vehicle_Brand_Image) {
            this.Vehicle_Brand_Image = Vehicle_Brand_Image;
        }

        public String getVehicle_Brand_Name() {
            return Vehicle_Brand_Name;
        }

        public void setVehicle_Brand_Name(String Vehicle_Brand_Name) {
            this.Vehicle_Brand_Name = Vehicle_Brand_Name;
        }

        public List<VehicleDetailsBean> getVehicle_Details() {
            return Vehicle_Details;
        }

        public void setVehicle_Details(List<VehicleDetailsBean> Vehicle_Details) {
            this.Vehicle_Details = Vehicle_Details;
        }

        public static class VehicleDetailsBean {
            /**
             * Fuel_Type : [{"_id":"5f1e692a9f12a81b3dd70890","Fuel_Type":"Petrol","Background_Color":"#802A2A","updatedAt":"2020-07-27T05:42:02.196Z","createdAt":"2020-07-27T05:42:02.196Z","__v":0},{"_id":"5f1e69aa9f12a81b3dd70891","Fuel_Type":"Diesel","Background_Color":"#008000","updatedAt":"2020-07-27T05:44:10.895Z","createdAt":"2020-07-27T05:44:10.895Z","__v":0}]
             * Vehicle_Model : [{"_id":"5f1af058de7bf45b602f8bb1","VehicleModel_Name":"Hatchbact","VehicleModel_Image":"http://3.101.31.129:3000/api/uploads/63f26304-9f74-4132-8e28-da33dd4f9214.png","Vehicle_Type":"Four Wheeler","updatedAt":"2020-07-24T14:29:44.857Z","createdAt":"2020-07-24T14:29:44.857Z","__v":0}]
             * _id : 5f1e6a299f12a81b3dd70892
             * Vehicle_Brand_id : 5f1a842467024c39a738e821
             * Vehicle_Image : http://3.101.31.129:3000/api/uploads/0d09b03d-4ea1-42f2-8a53-a622500e0f0a.jpg
             * Vehicle_Name : AUDI A1
             * updatedAt : 2020-07-27T07:48:23.832Z
             * createdAt : 2020-07-27T05:46:17.517Z
             * Vehicle_CC :
             * mfg_year_start : 1934
             * mfg_year_end : 1951
             * __v : 0
             */

            private String _id;
            private String Vehicle_Brand_id;
            private String Vehicle_Image;
            private String Vehicle_Name;
            private String updatedAt;
            private String createdAt;
            private String Vehicle_CC;
            private int mfg_year_start;
            private int mfg_year_end;
            private int __v;
            private List<FuelTypeBean> Fuel_Type;
            private List<VehicleModelBean> Vehicle_Model;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getVehicle_Brand_id() {
                return Vehicle_Brand_id;
            }

            public void setVehicle_Brand_id(String Vehicle_Brand_id) {
                this.Vehicle_Brand_id = Vehicle_Brand_id;
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

            public String getVehicle_CC() {
                return Vehicle_CC;
            }

            public void setVehicle_CC(String Vehicle_CC) {
                this.Vehicle_CC = Vehicle_CC;
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
                 * _id : 5f1af058de7bf45b602f8bb1
                 * VehicleModel_Name : Hatchbact
                 * VehicleModel_Image : http://3.101.31.129:3000/api/uploads/63f26304-9f74-4132-8e28-da33dd4f9214.png
                 * Vehicle_Type : Four Wheeler
                 * updatedAt : 2020-07-24T14:29:44.857Z
                 * createdAt : 2020-07-24T14:29:44.857Z
                 * __v : 0
                 */

                private String _id;
                private String VehicleModel_Name;
                private String VehicleModel_Image;
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
}
