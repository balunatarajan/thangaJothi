import { Component } from '@angular/core';
import {TranInfo} from './tran-info';
import {Count} from './../other/Count';
import {TransactionService} from '../../services/transaction/transaction.service'
import {TranCode} from '../trancode/tran-code';
@Component({
  selector: 'trans-list',
  templateUrl: '../../../tmpls/transaction/trans-list.tmpl.html',
 // templateUrl: '../../../tmpls/transaction/trans-add.tmpl.html',
  providers: [TransactionService]
  //styleUrls:['./list-comp.styles.css']
})
export class TransactionList { 

  trust = 'Thanga Jothi Gnana Sabai';
  getData : string;
  
  transList: Array<TranInfo>=[];
  trans: TranInfo;

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
  
 constructor(private as:TransactionService) {

    this.showDetail = 0; 
    this.currentPageNo = 0;
    this.pageSize = 5;
    this.transListCount = 0;
    this.selectedItem = -1;
    this.nonEdit = true;
    this.transCount.count= "0";
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
          console.log('transaction.list.ts : getAnbars before service');
          
          this.as.getTransactionByPage(this.pageSize,this.currentPageNo).subscribe(
            data => this.transList = data,
            err => console.log(err),
            () =>this.transListCount = this.transList.length
          );
          
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
    //this.anbar = selectedAnbar;
    this.trans = Object.assign({}, selectedTrans);  // deep copy 
    this.showDetail = 1;
  }


}
