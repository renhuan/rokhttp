package com.example.administrator.myokhttp;

public class Bean {


    /**
     * footstep : 371
     * create_time : 2019-11-01 12:50:38
     * user_id : 2912
     * phone : 15044705151
     * happen_time : 2019-11-01 12:50:36
     * hardware_code : 867957242012352
     * nickname : 王蕾
     * backpack_code : 1197084038
     * fake : 0
     * id : 5062088
     * visitor : 0
     * status : 1
     */

    private int footstep;
    private String create_time;
    private int user_id;
    private String phone;
    private String happen_time;
    private String hardware_code;
    private String nickname;
    private String backpack_code;
    private int fake;
    private int id;
    private int visitor;
    private int status;

    public int getFootstep() {
        return footstep;
    }

    public void setFootstep(int footstep) {
        this.footstep = footstep;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHappen_time() {
        return happen_time;
    }

    public void setHappen_time(String happen_time) {
        this.happen_time = happen_time;
    }

    public String getHardware_code() {
        return hardware_code;
    }

    public void setHardware_code(String hardware_code) {
        this.hardware_code = hardware_code;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBackpack_code() {
        return backpack_code;
    }

    public void setBackpack_code(String backpack_code) {
        this.backpack_code = backpack_code;
    }

    public int getFake() {
        return fake;
    }

    public void setFake(int fake) {
        this.fake = fake;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVisitor() {
        return visitor;
    }

    public void setVisitor(int visitor) {
        this.visitor = visitor;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" +
                "步数=" + footstep +
                ", 时间='" + create_time + '\'' +
                ", 手机号='" + phone + '\'' +
                ", 硬件编码='" + hardware_code + '\'' +
                ", 昵称='" + nickname + '\'' +
                ", 背包编码='" + backpack_code + '\'' +
                ", 数据状态=" + (status == 1 ? "有效" : "无效") +
                '}';
    }
}
