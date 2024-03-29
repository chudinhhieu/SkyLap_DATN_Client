package com.example.skylap_datn_md03.data.models;

import java.util.Date;
import java.util.List;

public class Message {
//    idChat: idChat,
//    idAccount: idNguoiGui,
//    content: content,
//    AnhTinNhan: [],


    private String id;
    private String idChat;
    private String idAccount;
    private String content;
    private List<String> AnhTinNhan;
    private boolean Daxem;
    private boolean ThuHoi;
    private String thoiGian;

    public Message(String id, String idChat, String idAccount, String content, List<String> anhTinNhan, boolean daxem, boolean thuHoi, String thoiGian) {
        this.id = id;
        this.idChat = idChat;
        this.idAccount = idAccount;
        this.content = content;
        AnhTinNhan = anhTinNhan;
        Daxem = daxem;
        ThuHoi = thuHoi;
        this.thoiGian = thoiGian;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public Message() {
    }

    public boolean isDaxem() {
        return Daxem;
    }



    public void setDaxem(boolean daxem) {
        Daxem = daxem;
    }

    public boolean isThuHoi() {
        return ThuHoi;
    }

    public void setThuHoi(boolean thuHoi) {
        ThuHoi = thuHoi;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getIdChat() {
        return idChat;
    }

    public void setIdChat(String idChat) {
        this.idChat = idChat;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getAnhTinNhan() {
        return AnhTinNhan;
    }

    public void setAnhTinNhan(List<String> anhTinNhan) {
        AnhTinNhan = anhTinNhan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
