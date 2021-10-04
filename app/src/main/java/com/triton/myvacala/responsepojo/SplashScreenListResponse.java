package com.triton.myvacala.responsepojo;

import java.util.List;

public class SplashScreenListResponse {

    /**
     * Status : Success
     * Message : Mobile Splash Git Details
     * Data : [{"_id":"5f1ae36ddb0a32519d6e7252","gif_path":"http://3.101.31.129:3000/api/uploads/sun_and_seasons2.gif","updatedAt":"2020-07-24T13:34:37.476Z","createdAt":"2020-07-24T13:34:37.476Z","__v":0},{"_id":"5f1ae3a7db0a32519d6e7253","gif_path":"http://3.101.31.129:3000/api/uploads/a3573c74725a2b3df26ff600095a258c.gif","updatedAt":"2020-07-24T13:35:35.708Z","createdAt":"2020-07-24T13:35:35.708Z","__v":0}]
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
         * _id : 5f1ae36ddb0a32519d6e7252
         * gif_path : http://3.101.31.129:3000/api/uploads/sun_and_seasons2.gif
         * updatedAt : 2020-07-24T13:34:37.476Z
         * createdAt : 2020-07-24T13:34:37.476Z
         * __v : 0
         */

        private String _id;
        private String gif_path;
        private String updatedAt;
        private String createdAt;
        private int __v;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getGif_path() {
            return gif_path;
        }

        public void setGif_path(String gif_path) {
            this.gif_path = gif_path;
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
