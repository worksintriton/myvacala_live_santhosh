package com.triton.myvacala.responsepojo;

import java.util.List;

public class FAQResponse {


    /**
     * Status : Success
     * Message : FAQ Details
     * Data : [{"_id":"5f0d529e8760ac7605a47470","Question":"What is myvacala","Answer":"myvacala is mechanic booking app","updatedAt":"2020-07-14T06:37:18.418Z","createdAt":"2020-07-14T06:37:18.418Z","__v":0}]
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
         * _id : 5f0d529e8760ac7605a47470
         * Question : What is myvacala
         * Answer : myvacala is mechanic booking app
         * updatedAt : 2020-07-14T06:37:18.418Z
         * createdAt : 2020-07-14T06:37:18.418Z
         * __v : 0
         */

        private String _id;
        private String Question;
        private String Answer;
        private String updatedAt;
        private String createdAt;
        private int __v;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getQuestion() {
            return Question;
        }

        public void setQuestion(String Question) {
            this.Question = Question;
        }

        public String getAnswer() {
            return Answer;
        }

        public void setAnswer(String Answer) {
            this.Answer = Answer;
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
