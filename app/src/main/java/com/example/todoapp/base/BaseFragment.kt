package com.example.todoapp.base

import android.app.ProgressDialog
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment(){
    var progressDialog: ProgressDialog?=null
    fun showLoadingDialog(){
        progressDialog= ProgressDialog(requireContext())
        progressDialog?.setMessage("loading...")
        progressDialog?.show()
    }
    fun hideLoading(){
        progressDialog?.dismiss()
    }

    //alertDialog
    var alertDialog: AlertDialog?=null
    fun showMessage(message : String, postActionTitle:String?=null,
                    postAction: DialogInterface.OnClickListener?=null,
                    negActionTitle:String?=null,
                    negAction: DialogInterface.OnClickListener?=null,
                    cancelable:Boolean = true)
    {
        var messageDialogBuilder = AlertDialog.Builder(requireContext())
        messageDialogBuilder.setMessage(message)
        if(postActionTitle!=null){
            messageDialogBuilder.setPositiveButton(postActionTitle,
                postAction?: DialogInterface.OnClickListener{
                        dialog, which ->  dialog.dismiss()
                })
        }
        if (negActionTitle!=null){
            messageDialogBuilder.setNegativeButton(negActionTitle,
                negAction?: DialogInterface.OnClickListener{
                        dialog, which ->  dialog.dismiss()
                })
        }
        messageDialogBuilder.setCancelable(cancelable)
        alertDialog=messageDialogBuilder.show()
    }
}