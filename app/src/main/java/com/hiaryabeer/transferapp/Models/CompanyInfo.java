package com.hiaryabeer.transferapp.Models;

public class CompanyInfo {
    // {
    //    "CoNo": "299",
    //    "CoYear": "2021",
    //    "CoNameA": "Al Rayyan Plastic Factory 2016"
    //  }
    private  String CoNo;
    private  String CoYear;
    private  String CoNameA;

    public CompanyInfo() {
    }

    public String getCoNo() {
        return CoNo;
    }

    public void setCoNo(String coNo) {
        CoNo = coNo;
    }

    public String getCoYear() {
        return CoYear;
    }

    public void setCoYear(String coYear) {
        CoYear = coYear;
    }

    public String getCoNameA() {
        return CoNameA;
    }

    public void setCoNameA(String coNameA) {
        CoNameA = coNameA;
    }
}
