package com.shahruie.www.gohome;

/**
 * Created by MR on 05/09/2017.
 */
public class Addresses {
    private String imagepath;
        private int id;
        private String name;
        private String address;
        private String phone;
        private String voice;
        public Addresses()
        {
        }
        public Addresses(int id,String address,String name,
                         String phone,String imagepath,String voice)
        {
            this.id=id;
            this.name=name;
            this.phone=phone;
            this.voice=voice;
            this.address=address;
            this.imagepath =imagepath;
        }
       public void setId(int id) {
            this.id = id;
        }
        public void setName(String name) {
            this.name = name;
        }
        public void setPhone(String phone) {
            this.phone=phone;
        }
        public void setVoice(String voice) {
            this.voice=voice;
        }
        public void setAddress(String address) {
            this.address = address;
        }
        public void setimg(String imageBytes) {
            this.imagepath =imageBytes;
    }

        public int getId() {
            return id;
        }
        public String getAddress() {
            return address;
        }
        public String getName() {
            return name;
        }
        public String getPhone() {
            return phone;
        }
        public String getVoice() {
            return voice;
        }
        public String getimg() {
        return imagepath;
    }



}
