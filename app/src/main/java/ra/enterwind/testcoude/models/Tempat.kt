package ra.enterwind.testcoude.models

class Tempat {

    var id: String? = null
    var daerah:String? = null
    var harga:String? = null
    var waktu:String? = null

    constructor()

    constructor(id:String, daerah:String, harga:String, waktu:String){
        this.id = id
        this.daerah = daerah
        this.harga = harga
        this.waktu = waktu
    }

}