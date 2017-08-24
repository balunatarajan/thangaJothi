import { Component } from '@angular/core';
import {TranInfo} from './tran-info';
import {Count} from './../other/Count';
import {TransactionService} from '../../services/transaction/transaction.service'
import {TranCode} from '../trancode/tran-code';
import {TranSearch} from './tran-search';
import * as jsPDF from 'jspdf'
import {DatePipe} from '@angular/common';
//let jsPDF = require('jspdf');

@Component({
  selector: 'trans-list',
  templateUrl: '../../../tmpls/transaction/trans-list.tmpl.html',
  //templateUrl: '../../../tmpls/transaction/trans-add.tmpl.html',
  providers: [DatePipe,TransactionService] 
  //styleUrls:['./list-comp.styles.css']
})
export class TransactionList { 

  trust = 'Thanga Jothi Gnana Sabai';
  getData : string;
  enablePaginationButton:boolean = false;  
  transList: Array<TranInfo>=[];
  trans: TranInfo = new TranInfo;
  tranSearch:TranSearch = new TranSearch;
  showDetail:number;
  currentPageNo : number;
  pageSize: number;
  transListCount : number;
  selectedItem : number;
  nonEdit:boolean;
  mode:string;
  totalRecords:number;
  pageList:Array<number> = new Array(4);
  // pageList:number[] = new Array(4)  
  totalPages : number;
  transCount : Count = new Count;
  tCodeList: Array<TranCode>=[];
  selectedTranDesc:string;  
  selectedTranCode:string;  
  selectedToDate:Date;
  selectedFromDate:Date;
  selectedDesc:string;
  selectedVoucher:string
 constructor(private as:TransactionService, private dp:DatePipe) {
    //this.selectedToDate = Date.now();
    this.showDetail = 0; 
    this.currentPageNo = 0;
    this.pageSize = 5;
    this.transListCount = 0;
    this.selectedItem = -1;
    this.nonEdit = true;
    this.transCount.count= "0";
    this.selectedTranDesc = '';
    this.totalPages = this.totalRecords / this.pageSize; 
    this.populatePageArray();
  }
  
  ngOnInit(){
     this.getTransactionCountInDb();    //this.totalRecords = 59;
     this.totalPages = this.totalRecords / this.pageSize; 
     this.getTranCodes();
  }

getTranCodes(){
         this.tCodeList.length = 0;
          console.log(':getAllTranCode before service');
          
          this.as.getAllTranCode().subscribe(
            data => this.tCodeList = data,
            err => console.log(err),
            () =>{this.transListCount = this.transList.length;console.log('getAlltranCode returns'+this.transList.length);}
          );
        
}

printTranList(){
    for (var i = 0; i < this.transList.length; i++)
    {
      console.log('Tran I:'+i+this.transList[i].description);
    }
}

private populatePageArray() {
  
    if(this.currentPageNo >= 0 && this.totalPages > 0){
      let idx = this.pageList.length;
      this.pageList.splice(0,idx);
      idx = 0;
      for(let i = this.currentPageNo ; i < this.totalPages ; i++){
        this.pageList.push(i);
        idx++;
        if(idx == 4) break;
      }
    } 
  }

    // when radio button is selected 
onSelection(selectedTrans: TranInfo){
      console.log('Radio button selected ');
      //this.anbar = selectedAnbar;
}

    //selectPage event 
selectPage(selectedPage:number){
        console.log('page selected  ' + selectedPage);
        this.currentPageNo = selectedPage;
        this.getTransByPage();
    }
    
//<< >> button pressed
setPageNumber(idx:number){
      this.showDetail = 0;
      if(idx == -1){
            if(this.currentPageNo > 0){
              this.currentPageNo --;
              this.getTransByPage();
               this.populatePageArray();
            }
      }
      else if(idx == -2){
           if(this.currentPageNo >= 0 && this.currentPageNo + 4 < this.totalPages ){
              this.currentPageNo ++;
              this.getTransByPage();

               this.populatePageArray();
            }
      }
     
    }

getTransByPage(){
          
          //this.transList=[];
          this.transList.length = 0;
          console.log('Enter getTransByPage');
          console.log('getTransByPage() selectedTranCode'+  this.selectedTranCode); 
          console.log('getTransByPage() selectedToDate'+  this.selectedToDate); 
          console.log('getTransByPage() selectedFromDate'+  this.selectedFromDate); 
          console.log('getTransByPage() selectedDesc'+  this.selectedDesc); 
          console.log('getTransByPage() selectedVoucher'+  this.selectedVoucher); 
          var bFilter = false;
          this.tranSearch.tcLike = "*";
          this.tranSearch.fDate = '*';
          this.tranSearch.tDate = '*';
          this.tranSearch.descLike = "*";
          this.tranSearch.voucherLike = "*";         
          if(this.selectedTranCode){
                this.tranSearch.tcLike = this.selectedTranCode;
                bFilter = true;
          }
      
          if(this.selectedFromDate){
                this.tranSearch.fDate = (this.selectedFromDate).toString();
                bFilter = true;
          }
          else{
          //  var tmp = new Date(1,1,1);
            //this.tranSearch.fDate = tmp
            
          }
          if(this.selectedToDate){
                this.tranSearch.tDate = this.selectedToDate.toString();
                bFilter = true;
          }
        else{
           // var tmp = new Date(1,1,1);
           //  this.tranSearch.tDate = tmp
          }
          if(this.selectedDesc) {
                this.tranSearch.descLike = this.selectedDesc;
                bFilter = true;
          }

          if(this.selectedVoucher) {
                this.tranSearch.voucherLike = this.selectedVoucher;
                bFilter = true;
          }

          if(bFilter) {
            this.enablePaginationButton = false;
            this.as.getAllTransByCriteria(this.tranSearch).subscribe(
            data => this.transList = data,
            err => console.log(err),
            () =>{this.transListCount = this.transList.length; 
                    this.totalPages = this.totalRecords / this.pageSize; 
                    this.populatePageArray();}
            );

          }
          else{  
            this.enablePaginationButton = true;
            this.as.getTransactionByPage(this.pageSize,this.currentPageNo).subscribe(
              data => this.transList = data,
              err => console.log(err),
              () =>{this.transListCount = this.transList.length; 
                    this.totalPages = this.totalRecords / this.pageSize; 
                    this.populatePageArray();}
            );
          }
          this.nonEdit = true;
          this.mode = '';
          this.showDetail = 0; 
          console.log('Total RECORDs'+this.totalRecords);
          this.totalPages = this.totalRecords / this.pageSize; 
          this.populatePageArray();
  }
  
  
// delete button
deleteTransaction(){
  //make service && call list again to get new list 
}

//modify button  
modifyTransaction(){
  
      this.mode ='M';
      this.nonEdit = false;

   } 
  
  //add button
addNewTransaction(){
  
    this.mode ='A';
    this.resetTrans();    
    this.nonEdit = false;
    this.showDetail = 1;

  }

getTransactionCountInDb(){

     console.log('transaction.list.ts : getTransactionCountInDb');     
     this.as.getTransCountInDb().subscribe(
          data => this.transCount = data,
          err => console.log('--ERROR---'+err),
          ()=>this.totalRecords = parseInt(this.transCount.count)
          );
  }

  //copy button
copyNewTransaction(){

    this.nonEdit = false;
    this.mode ='C';
    this.trans.tranId = '';
    
  }

//save button 
saveButtonClicked() {
    
    console.log('saveButtonClicked mode = ' + this.mode);
    if( this.mode == 'C' || this.mode == 'A'){
     
      
        this.as.addNewTransaction(this.trans)
               .subscribe(
                          data => this.getData =  JSON.stringify(data),
                          error => alert('ALERT __'+error),
                          () => this.getTransByPage()
                         );
    }
    else if( this.mode =='M'){
        
        this.as.modifyTransaction(this.trans)
               .subscribe(
                          data => this.getData =  JSON.stringify(data),
                          error => alert('ALERT __'+error),
                          ()=> this.getTransByPage()
                         ); 
    } 

    this.nonEdit = true;
    this.mode = '';
    this.showDetail = 0; 
  } 
  
  //cancel 
cancelButtonClicked() {
    this.showDetail = 0;
}

private  resetTrans(){

       // this.trans.transDate = '';
        this.trans.description = '';
        this.trans.amount = 0;
        this.trans.inExp = '';
        this.trans.headerCode = '';
        this.trans.voucherNo = '';
        this.trans.otherInfo = '';
        this.trans.userName = '';
        this.trans.tranId = '';
   }

selectCall(selectedTrans: TranInfo){
  
    this.nonEdit = true;
    
    this.trans = Object.assign({}, selectedTrans);  // deep copy 
    this.showDetail = 1;
  }

 selectTranCode(tranCode:string){
  this.selectedTranCode = tranCode;
  this.selectedTranDesc ='';
  console.log('selecte tran code'+ tranCode);
  this.tCodeList.forEach(element => {
    if(element.tranCode == tranCode)
    {
      this.selectedTranDesc = element.tranDesc;
      return;
    }
  });
  
 }
 addNewTranscation(){
   this.nonEdit = false;
   this.mode = 'A';
 }

savePDF(){
//import * as jsPDF from 'jspdf'
        var doc = new jsPDF();
        doc.setFontSize(16);
        doc.text(60, 20, 'THANGA JOTHI GNANA SABAI TRUST');
        doc.setFontSize(14);
        doc.text(50, 27, 'SAMARASA SUTHA SANMAARKA SATHIYA SANGAM');
        doc.setFontSize(11);
        doc.text(90, 33, 'www.vallalyaar.com');
        doc.setFontSize(10);
        doc.rect(10, 36, 190, 1, 'S');
        
        // Title
        doc.text(10, 42,"Date");
        doc.text(40, 42,"Description");
        doc.text(130, 42,"Credit(Rs)");
        doc.text(150, 42,"Debit(Rs)");
        doc.text(180, 42,"VoucherNo");
        let start = 45; 
        let cnt = 1;
        for (var i = 0; i < this.transList.length; i++)
        {
          let setDot = (this.dp).transform(this.transList[i].transDate, 'dd/MM/yyyy');
          
          doc.text(10, start+((cnt)  *5),setDot);
          doc.text(40, start+((cnt)  *5),this.transList[i].description);
          
          var amt = (this.transList[i].amount).toString();
         
          if(this.transList[i].inExp == 'e')
            doc.text(130, start+((cnt)  *5),amt);
          else
            doc.text(150, start+((cnt)  *5),amt);
          
          doc.text(180, start+((cnt) *5),this.transList[i].voucherNo);
          cnt ++;
          if(i>0 && i%50 == 0){
            start = 20;
            cnt = 1 ;
            doc.addPage();
          }
          
        }
       // doc.addPage();
       // doc.text(20, 20, 'Do you like that?');


        // Save the PDF
        doc.save('Test.pdf');
}

}
