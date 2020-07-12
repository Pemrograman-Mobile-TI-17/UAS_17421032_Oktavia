const tv = require('../model/Tv.js')
const response = require('../config/response')
const mongoose = require('mongoose')
const ObjectId = mongoose.Types.ObjectId
exports.inputDataTv = (data, gambar) =>
    new Promise(async (resolve, reject) =>{

        const tvbaru = new tv({
            kodeTv : data.kodeTv,
            merkTv : data.merkTv,
            tahunPembuatan: data.tahunPembuatan,
            hargaTv: data.hargaTv,
            gambar: gambar
        })

        await tv.findOne({kodeTv: data.kodeTv})
            .then(tv =>{
                if (tv){
                    reject(response.commonErrorMsg('Kode Tv Sudah Digunakan'))
                }else{
                    tvBaru.save()
                        .then(r =>{
                            resolve(response.commonSuccessMsg('Berhasil Menginput Data'))
                        }).catch(err =>{
                        reject(response.commonErrorMsg('Mohon Maaf Input Buku Gagal'))
                    })
                }
            }).catch(err =>{
                reject(response.commonErrorMsg('Mohon Maaf Terjadi Kesalahan Pada Server kami'))
            })
    })

exports.lihatDataTv = () =>
    new Promise(async (resolve, reject) =>{
        await tv.find({})
            .then(result =>{
                resolve(response.commonResult(result))
            })
            .catch(()=>reject(response.commonErrorMsg('Mohon Maaf Terjadi Kesalahan Pada Server kami')))
    })

exports.lihatDetailDataTv = (kodeTv) =>
    new Promise(async (resolve, reject) =>{
        await tv.findOne({kodeTv: kodeTv})
            .then(result =>{
                resolve(response.commonResult(result))
            })
            .catch(()=>reject(response.commonErrorMsg('Mohon Maaf Terjadi Kesalahan Pada Server kami')))
    })

exports.updateTv = (id, data, gambar) =>
    new Promise(async (resolve, reject)=>{
        await tv.updateOne(
            {_id : ObjectId(id)},
            {
                $set: {
                    kodeTv : data.kodeTv,
                    merkTv : data.merkTv,
                    tahunPembuatan: data.tahunPembuatan,
                    hargaTv: data.hargaTv,
                    gambar: gambar
                }
            }
        ).then(tv =>{
            resolve(response.commonSuccessMsg('Berhasil Mengubah Data'))
        }).catch(err =>{
            reject(response.commonErrorMsg('Mohon Maaf Terjadi Kesalahan Pada Server kami'))
        })
    })

exports.hapustv = (_id) =>
    new Promise(async (resolve, reject) =>{
        await tv.remove({_id: ObjectId(_id)})
            .then(() =>{
                resolve(response.commonSuccessMsg('Berhasil Menghapus Data'))
            }).catch(() =>{
                reject(response.commonErrorMsg('Mohon Maaf Terjadi Kesalahan Pada Server kami'))
            })
    })