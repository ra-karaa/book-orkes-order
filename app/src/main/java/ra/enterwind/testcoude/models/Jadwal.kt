package ra.enterwind.testcoude.models

class Jadwal{

    var  id : String? = null
    var  acara : String? = null
    var  tgl_acara : String ? = null
    var  bulan : String ? = null
    var  tahun : String ? = null
    var  member : String ? = null
    var alamat : String ? = null
    var status : String ? = null


    constructor(id: String, acara: String, tgl_acara: String, bulan:String, tahun:String,
                member:String, alamat:String, status:String){
        this.id = id
        this.acara = acara
        this.tgl_acara = tgl_acara
        this.bulan = bulan
        this.tahun = tahun
        this.member  = member
        this.alamat = alamat
        this.status = status
    }
}



