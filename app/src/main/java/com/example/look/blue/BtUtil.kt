package com.example.look.blue

class BtUtil {
    var defaultStartActivity = true

    //  val mJCAPI: JcapiTool by lazy { JcapiTool.get() }
    var mDevice: BtDeviceBean? = null


    companion object {
        private var instance: BtUtil? = null
            get() {
                if (field == null) {
                    field = BtUtil()
                }
                return field
            }

        fun get(): BtUtil {
            return instance!!
        }
    }
}