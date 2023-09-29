package QL_Chua.Services;

import QL_Chua.Models.DonDangKys;
import QL_Chua.payload.request.DonDangKyRequest;

public interface IDonDangKy {
    String themDonDangKy(DonDangKys donDangKys);

    String duyetDonDangKy(DonDangKyRequest dondky);

}
