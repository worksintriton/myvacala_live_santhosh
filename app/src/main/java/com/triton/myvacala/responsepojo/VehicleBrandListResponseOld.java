package com.triton.myvacala.responsepojo;

import java.util.List;

public class VehicleBrandListResponseOld {

    /**
     * Status : Success
     * Message : Vehicledetails
     * Data : [{"_id":"5f0c1023c57e0c6a78bc9395","Vehicle_Type_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Brand_Image":"http://3.101.31.129:3000/api/uploads/New Project.png","Vehicle_Brand_Name":"BMW","Vehicle_name_list":[{"Fuel_Type":[{"_id":"5f0c12f831e1f8795ba172f7","Fuel_Name":"Petrol"},{"_id":"5f0c132331e1f8795ba172f8","Fuel_Name":"Diesel"}],"Vehicle_Model":[{"_id":"5f0c306f7f655108e6ca2ea7","Name":"Cabriolet","Image":"http://3.101.31.129:3000/api/uploads/carimage1.png"},{"_id":"5f0c30f87f655108e6ca2ea8","Name":"Coupe","Image":"http://3.101.31.129:3000/api/uploads/carimage2.png"}],"_id":"5f0c50a1c1362e2e5f2fce1a","Vehicle_Brand_id":"5f0c1023c57e0c6a78bc9395","Vehicle_Image":"http://3.101.31.129:3000/api/uploads/New Project (5).png","Vehicle_Name":"BMW","updatedAt":"2020-07-13T12:16:33.503Z","createdAt":"2020-07-13T12:16:33.503Z","__v":0},{"Fuel_Type":[{"_id":"5f0c12f831e1f8795ba172f7","Fuel_Name":"Petrol"},{"_id":"5f0c132331e1f8795ba172f8","Fuel_Name":"Diesel"},{"_id":"5f0c136a31e1f8795ba172f9","Fuel_Name":"Natural Gas"},{"_id":"5f0c132331e1f8795ba172f8","Fuel_Name":"Diesel"}],"Vehicle_Model":[{"_id":"5f0c306f7f655108e6ca2ea7","Name":"Cabriolet","Image":"http://3.101.31.129:3000/api/uploads/carimage1.png"},{"_id":"5f0c30f87f655108e6ca2ea8","Name":"Coupe","Image":"http://3.101.31.129:3000/api/uploads/carimage2.png"},{"_id":"5f0c30f87f655108e6ca2ea8","Name":"Coupe","Image":"http://3.101.31.129:3000/api/uploads/carimage2.png"}],"_id":"5f0c50d0c1362e2e5f2fce1b","Vehicle_Brand_id":"5f0c1023c57e0c6a78bc9395","Vehicle_Image":"http://3.101.31.129:3000/api/uploads/New Project (6).png","Vehicle_Name":"Hyundai","updatedAt":"2020-07-13T12:17:20.849Z","createdAt":"2020-07-13T12:17:20.849Z","__v":0}]},{"_id":"5f0c1081b61e9075cddc4d37","Vehicle_Type_id":"5f0c0cfc2f857d66950cf25f","Vehicle_Brand_Image":"http://3.101.31.129:3000/api/uploads/New Project (1).png","Vehicle_Brand_Name":"Toyoto","Vehicle_name_list":[{"Fuel_Type":[{"_id":"5f0c12f831e1f8795ba172f7","Fuel_Name":"Petrol"},{"_id":"5f0c136a31e1f8795ba172f9","Fuel_Name":"Natural Gas"},{"_id":"5f0c132331e1f8795ba172f8","Fuel_Name":"Diesel"},{"_id":"5f0c137c31e1f8795ba172fa","Fuel_Name":"Bio-diesel"}],"Vehicle_Model":[{"_id":"5f0c306f7f655108e6ca2ea7","Name":"Cabriolet","Image":"http://3.101.31.129:3000/api/uploads/carimage1.png"},{"_id":"5f0c30f87f655108e6ca2ea8","Name":"Coupe","Image":"http://3.101.31.129:3000/api/uploads/carimage2.png"}],"_id":"5f0d4d57892e18724abf9a52","Vehicle_Brand_id":"5f0c1081b61e9075cddc4d37","Vehicle_Image":"http://3.101.31.129:3000/api/uploads/bmw.jpg","Vehicle_Name":"BMW Model","updatedAt":"2020-07-14T06:14:47.571Z","createdAt":"2020-07-14T06:14:47.571Z","__v":0}]}]
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
         * _id : 5f0c1023c57e0c6a78bc9395
         * Vehicle_Type_id : 5f0c0cfc2f857d66950cf25f
         * Vehicle_Brand_Image : http://3.101.31.129:3000/api/uploads/New Project.png
         * Vehicle_Brand_Name : BMW
         * Vehicle_name_list : [{"Fuel_Type":[{"_id":"5f0c12f831e1f8795ba172f7","Fuel_Name":"Petrol"},{"_id":"5f0c132331e1f8795ba172f8","Fuel_Name":"Diesel"}],"Vehicle_Model":[{"_id":"5f0c306f7f655108e6ca2ea7","Name":"Cabriolet","Image":"http://3.101.31.129:3000/api/uploads/carimage1.png"},{"_id":"5f0c30f87f655108e6ca2ea8","Name":"Coupe","Image":"http://3.101.31.129:3000/api/uploads/carimage2.png"}],"_id":"5f0c50a1c1362e2e5f2fce1a","Vehicle_Brand_id":"5f0c1023c57e0c6a78bc9395","Vehicle_Image":"http://3.101.31.129:3000/api/uploads/New Project (5).png","Vehicle_Name":"BMW","updatedAt":"2020-07-13T12:16:33.503Z","createdAt":"2020-07-13T12:16:33.503Z","__v":0},{"Fuel_Type":[{"_id":"5f0c12f831e1f8795ba172f7","Fuel_Name":"Petrol"},{"_id":"5f0c132331e1f8795ba172f8","Fuel_Name":"Diesel"},{"_id":"5f0c136a31e1f8795ba172f9","Fuel_Name":"Natural Gas"},{"_id":"5f0c132331e1f8795ba172f8","Fuel_Name":"Diesel"}],"Vehicle_Model":[{"_id":"5f0c306f7f655108e6ca2ea7","Name":"Cabriolet","Image":"http://3.101.31.129:3000/api/uploads/carimage1.png"},{"_id":"5f0c30f87f655108e6ca2ea8","Name":"Coupe","Image":"http://3.101.31.129:3000/api/uploads/carimage2.png"},{"_id":"5f0c30f87f655108e6ca2ea8","Name":"Coupe","Image":"http://3.101.31.129:3000/api/uploads/carimage2.png"}],"_id":"5f0c50d0c1362e2e5f2fce1b","Vehicle_Brand_id":"5f0c1023c57e0c6a78bc9395","Vehicle_Image":"http://3.101.31.129:3000/api/uploads/New Project (6).png","Vehicle_Name":"Hyundai","updatedAt":"2020-07-13T12:17:20.849Z","createdAt":"2020-07-13T12:17:20.849Z","__v":0}]
         */

        private String _id;

        private String Vehicle_Type_id;
        private String Vehicle_Brand_Name;
        private String Vehicle_Brand_Image;
        private List<VehicleNameListBean> Vehicle_name_list;

        public DataBean(String _id, String vehicle_Type_id, String vehicle_Brand_Name, String vehicle_Brand_Image) {
            this._id = _id;
            Vehicle_Type_id = vehicle_Type_id;
            Vehicle_Brand_Name = vehicle_Brand_Name;
            Vehicle_Brand_Image = vehicle_Brand_Image;
        }

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

        public List<VehicleNameListBean> getVehicle_name_list() {
            return Vehicle_name_list;
        }

        public void setVehicle_name_list(List<VehicleNameListBean> Vehicle_name_list) {
            this.Vehicle_name_list = Vehicle_name_list;
        }

        public static class VehicleNameListBean {
            /**
             * Fuel_Type : [{"_id":"5f0c12f831e1f8795ba172f7","Fuel_Name":"Petrol"},{"_id":"5f0c132331e1f8795ba172f8","Fuel_Name":"Diesel"}]
             * Vehicle_Model : [{"_id":"5f0c306f7f655108e6ca2ea7","Name":"Cabriolet","Image":"http://3.101.31.129:3000/api/uploads/carimage1.png"},{"_id":"5f0c30f87f655108e6ca2ea8","Name":"Coupe","Image":"http://3.101.31.129:3000/api/uploads/carimage2.png"}]
             * _id : 5f0c50a1c1362e2e5f2fce1a
             * Vehicle_Brand_id : 5f0c1023c57e0c6a78bc9395
             * Vehicle_Image : http://3.101.31.129:3000/api/uploads/New Project (5).png
             * Vehicle_Name : BMW
             * updatedAt : 2020-07-13T12:16:33.503Z
             * createdAt : 2020-07-13T12:16:33.503Z
             * __v : 0
             */

            private String _id;
            private String Vehicle_Brand_id;
            private String Vehicle_Image;
            private String Vehicle_Name;
            private String updatedAt;
            private String createdAt;
            private int __v;
            private List<FuelTypeBean> Fuel_Type;
            private List<VehicleModelBean> Vehicle_Model;

            public VehicleNameListBean(String vehicleid, String brand_id, String vehicle_name) {
                this._id = vehicleid;
                this.Vehicle_Brand_id = brand_id;
                this.Vehicle_Name = vehicle_name;
            }

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
                public FuelTypeBean(String _id, String fuel_Name) {
                    this._id = _id;
                    Fuel_Name = fuel_Name;
                }

                /**
                 * _id : 5f0c12f831e1f8795ba172f7
                 * Fuel_Name : Petrol
                 */

                private String _id;
                private String Fuel_Name;


                public String get_id() {
                    return _id;
                }

                public void set_id(String _id) {
                    this._id = _id;
                }

                public String getFuel_Name() {
                    return Fuel_Name;
                }

                public void setFuel_Name(String Fuel_Name) {
                    this.Fuel_Name = Fuel_Name;
                }
            }

            public static class VehicleModelBean {


                /**
                 * _id : 5f0c306f7f655108e6ca2ea7
                 * Name : Cabriolet
                 * Image : http://3.101.31.129:3000/api/uploads/carimage1.png
                 */

                private String _id;
                private String Name;
                private String Image;
                private String nameListId;
                private String vehicle_brand_id;
                private String vehicle_name;


                public VehicleModelBean(String _id, String name, String image, String nameListId,String vehicle_brand_id,String vehicle_name) {
                    this._id = _id;
                    Name = name;
                    Image = image;
                    this.nameListId = nameListId;
                    this.vehicle_brand_id  = vehicle_brand_id;
                    this.vehicle_name  = vehicle_name;
                }

                public String get_id() {
                    return _id;
                }

                public void set_id(String _id) {
                    this._id = _id;
                }

                public String getName() {
                    return Name;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }

                public String getImage() {
                    return Image;
                }

                public void setImage(String Image) {
                    this.Image = Image;
                }

                public String getNameListId() {
                    return nameListId;
                }

                public void setNameListId(String nameListId) {
                    this.nameListId = nameListId;
                }

                public String getVehicle_brand_id() {
                    return vehicle_brand_id;
                }

                public void setVehicle_brand_id(String vehicle_brand_id) {
                    this.vehicle_brand_id = vehicle_brand_id;
                }

                public String getVehicle_name() {
                    return vehicle_name;
                }

                public void setVehicle_name(String vehicle_name) {
                    this.vehicle_name = vehicle_name;
                }
            }
        }
    }
}
