package ra.enterwind.testcoude.utils

/*
    * author <Fieta Prabaskara>
    * fprabaskara@gmail.com
*/


class Endpoint {

    var url_base = "https://oke.sansproject.id/api/v1/"

    //    untuk auth member
    var url_register = url_base + "register"
    val url_login = url_base + "login/"

    /*
        * untuk melihat jadwal yang yang telah di pesanan
    */
    val url_jadwal = url_base + "sewa"

    //kegunaan untuk pemesanan
    val url_buat = url_base + "buat/"
    val url_jadwaku = url_base + "sewaku/"
    val url_jadwal_detail = url_base + "jadwal/"
    val url_upload = url_base + "struk"

    //untuk ambil api alamat yang tersedia
    val url_daerah = url_base + "alamat"

    //untuk update password dan profil
    val url_profil = url_base + "profil/update"
    val url_password = url_base + "password/update"

}