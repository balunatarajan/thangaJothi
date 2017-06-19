import { Component } from '@angular/core';
import {TranCode} from './tran-code';
import {Count} from './../other/Count';
import {TransactionService} from '../../services/transaction/transaction.service'

@Component({
  selector: 'tcode-list',
  templateUrl: '../../../tmpls/trancode/tran-code-list.html',
  providers: [TransactionService]
  //styleUrls:['./list-comp.styles.css']
})
export class TranCodeList { 

  trust = 'Thanga Jothi Gnana Sabai';
  getData : string;
  
  transList: Array<TranCode>=[];
  trans: TranCode;

  showDetail:number;
  
  transListCount : number;
  selectedItem : number;
  nonEdit:boolean;
  mode:string;
  totalRecords:number;
  transCount : Count = new Count;
  touch: boolean;
  filterOdd: boolean;
  

 constructor(private as:TransactionService) {

    this.showDetail = 0; 
   
    this.transListCount = 0;
    this.selectedItem = -1;
    this.nonEdit = true;
    this.transCount.count= "0";
   }
  
  ngOnInit(){
     console.log('trancode.list.ts - ngOnInit() ')
     this.getTranCodeCountInDb();    //this.totalRecords = 59;
   }

    // when radio button is selected 
    onSelection(selectedTrans: TranCode){
      console.log('Radio button selected ');
      //this.anbar = selectedAnbar;
    }

  
   getAllTranCode(){
          
          //this.transList=[];
          this.transList.length = 0;
          console.log('trancode.list.ts :getAllTranCode before service');
          
          this.as.getAllTranCode().subscribe(
            data => this.transList = data,
            err => console.log(err),
            () =>{this.transListCount = this.transList.length;console.log('getAlltranCode returns'+this.transList.length);}
          );
          
          this.nonEdit = true;
          this.mode = '';
          this.showDetail = 0;
 }
  
  
  //modify button  
  modifyTransaction(){
  
      this.mode ='M'; 
      this.nonEdit = false;
        
   } 
  
  //add button
  addNewTransaction(){
  
    this.mode ='A';
    this.trans.tranCodeId = 0;
    this.resetTrans();    
    this.nonEdit = false;
    this.showDetail = 1;

  }

  getTranCodeCountInDb(){

     console.log('transaction.list.ts : getTranCodeCountInDb');     
     this.as.getTransCodeInDb().subscribe(
          data => this.transCount = data,
          err => console.log('--ERROR---'+err),
          ()=>this.totalRecords = parseInt(this.transCount.count)
          );
  }

  //copy button
  copyNewTransaction(){

    this.nonEdit = false;
    this.mode ='C';
    this.trans.tranCodeId = 0;
  }

  //save button 
  saveButtonClicked() {
  
    if( this.mode == 'C' || this.mode == 'A'){
     
        this.as.addNewTranCode(this.trans)
               .subscribe(
                          data => this.getData =  JSON.stringify(data),
                          error => alert('ALERT __'+error),
                          () => this.getAllTranCode()
                         );
    }
    else if( this.mode =='M'){
        
        this.as.modifyTranCode(this.trans)
               .subscribe(
                          data => this.getData =  JSON.stringify(data),
                          error => alert('ALERT __'+error),
                          ()=> this.getAllTranCode()
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

        this.trans.tranCode = '';
        this.trans.tranDesc = '';
}

  
  selectCall(selectedTrans: TranCode){
  
    this.nonEdit = true;
    //this.anbar = selectedAnbar;
    this.trans = Object.assign({}, selectedTrans);  // deep copy 
    this.showDetail = 1;
  }


}
