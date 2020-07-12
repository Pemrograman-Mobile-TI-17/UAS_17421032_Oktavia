const mongoose = require('mongoose');

const userSchema = mongoose.Schema({

    kodeTv: {
        type: String
    },
    merkTv: {
        type: String
    },
    tahunPembuatan: {
        type: String
    },
    hargaTv: {
        type: String
    },
    gambar: {
        type: String
    }
})
module.exports = mongoose.model('tv', userSchema)