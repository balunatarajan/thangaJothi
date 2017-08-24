import { Component } from '@angular/core';
import {BookService} from '../../services/books/book.services';
import {BookInfo} from './book-info';
import {MasterDataC} from './../other/masterData';
import {BookTran} from './booktran';
import {BookCount} from './book-count';
import {BookTransfer} from './book-transfer';
import {AnbarService} from '../../services/anbar/anbar.service';

enum Screen {
  eplaceFirst=1,
  ebookFirst=2,
  eaddStock=3,
  esellBook=4
}

@Component({
  selector: 'book-list',
  templateUrl: '../../../tmpls/books/book-list.tmpl.html',
  // templateUrl: '../../../tmpls/books/test.html',
  providers: [BookService,AnbarService]
  //, // worked without this how?
  //styleUrls:['./set2.css']
})
export class BookList { 
  placeNameList: Array<string>=[];
  bookOwnerList: Array<string>=[];
  bookNameList: Array<MasterDataC>=[];
  bookCountList: Array<BookCount>=[];
  trust = 'Thanga Jothi Gnana Sabai';
  currentScreen:number;
  nonEdit:boolean;

  placeFirst:boolean;
  bookFirst:boolean;
  addStock:boolean;
  sellBook:boolean;
  transferBook:boolean;
  donateBook:boolean;

  fullImagePath:string;
  bookList:Array<BookInfo>=[];
  bookListC:Array<MasterDataC>=[];
  bookInfo : BookInfo = new BookInfo;
  anbarList: Array<string> =[];
  filterAnbar:string;
  anbarNameSelectedList: string[];
  selectedAnbar:string;
  selectedBookQty:number;
  selectedBookTitle:string;
  selectedLocation:string;
  selectedToAnbar:string;

  bookTran:BookTran;
  bookTranList:Array<BookTran> =[];
  currentBookTranList:Array<BookTran> =[]; // selected in memory for add/sell 
  transferBookObj:BookTransfer;
  tranferBookList: Array<BookTransfer>=[];
  getData:string;
  clickedAnbar:number;

  constructor(private bs:BookService,private as:AnbarService){
      this.placeNameList.push("Hosur");
      this.placeNameList.push("Vadalur");
      this.placeNameList.push("Krishnamurthi");
      this.placeNameList.push("Saravanan");
      this.placeNameList.push("Babu");
      this.placeNameList.push("Logu");
      this.placeNameList.push("Coimbatore");


     /*  this.anbarList.push("Hosur");
      this.anbarList.push("Vadalur");
      this.anbarList.push("Krishnamurthi");
      this.anbarList.push("Saravanan");
      this.anbarList.push("Babu");
      this.anbarList.push("Logu");
      this.anbarList.push("Coimbatore");
       this.anbarList.push("Trichy");
      this.anbarList.push("Bangalore");
      this.anbarList.push("Balu");
      this.anbarList.push("Saritha");
      this.anbarList.push("Suruli");
      this.anbarList.push("Aravind");
      this.anbarList.push("Vimal");*/
      
      /*this.bookNameList.push("Kanmani Maalai");
      this.bookNameList.push("Arulmani Maalai");
      this.bookNameList.push("SagaKalvi");
      this.bookNameList.push("Vallalyaar");
      this.bookNameList.push("Manthiramani Maalai");
      this.bookNameList.push("Gnanam Pera Vili");
      this.bookNameList.push("Anma neya orumaipaadu");*/
      this.nonEdit = false;
      this.bookFirst = true;
      this.placeFirst = false;
      this.addStock = false;
      this.transferBook = false;
      this.sellBook = false;
      this.fullImagePath ='images/Books/';
      this.selectedBookTitle = "";
      this.bookInfo.bookImgName="B1.jpg";
      this.bookInfo.bookName="Kanmani Maalai";
      this.bookList.push(this.bookInfo);
      
      this.bookInfo = new BookInfo;
      this.bookInfo.bookImgName="B2.jpg";
      this.bookInfo.bookName="Arulmani Maalai"
      this.bookList.push(this.bookInfo);

      this.bookInfo = new BookInfo;
      this.bookInfo.bookImgName="B3.jpg";
      this.bookInfo.bookName="Sagakalvoi";
      this.bookList.push(this.bookInfo);

      this.bookInfo = new BookInfo;
      this.bookInfo.bookImgName="B4.jpg";
      this.bookInfo.bookName="Vallalyaar"
      this.bookList.push(this.bookInfo);

    this.getAllBookNames();
    this.getBookTranGroupBy();
    this.currentScreen = Screen.eplaceFirst;
}  
  
ngOnInit(){
    this.getBookTranGroupBy();
    this.getallAnbarByName();
    this.getBookOwners();

}


getBookOwners(){
    this.bs.getBookOwners().subscribe(
          data => this.bookOwnerList = data,
          err => console.log(err),
          () => {
              console.log('getBookOwners returned '+name+'-count ='+ this.bookOwnerList.length);
              }
           );
}

getallAnbarByName(){

    this.as.getAnbarAllNames().subscribe(
          data => this.anbarList = data,
          err => console.log(err),
          () => {
              console.log('anbarList returned '+name+'-count ='+ this.anbarList.length);
              }
           );
}

selectBookName(name: MasterDataC){
        this.bs.getBookTranByTitle(name.dataName).subscribe(
          data => this.bookTranList = data,
          err => console.log(err),
          () => {
              console.log('bookTransaction returned '+name+'-count ='+ this.bookTranList.length);
              }
           );
}
populateCurrentStock(){
    if(this.selectedAnbar != null && this.selectedBookTitle != null && 
    this.selectedAnbar.length > 0 && this.selectedBookTitle.length > 0)
    {
        this.bs.getBookTranByOwnerTitle(this.selectedAnbar,this.selectedBookTitle).subscribe(
          data => this.currentBookTranList = data,
          err => console.log(err),
          () => {console.log('bookTransaction returned '+this.selectedAnbar+'-count ='+ this.currentBookTranList.length);
              }

          );   
    }
    else if(this.selectedAnbar != null && this.selectedAnbar.length > 0 ){

        this.bs.getBookTranByOwner(this.selectedAnbar).subscribe(
          data => this.currentBookTranList = data,
          err => console.log(err),
          () => {console.log('bookTransaction returned '+this.selectedAnbar+'-count ='+ this.currentBookTranList.length);
              }

          );
    }
    else if( this.selectedBookTitle != null && this.selectedBookTitle.length > 0 ){    
         this.bs.getBookTranByTitle(this.selectedBookTitle).subscribe(
          data => this.currentBookTranList = data,
          err => console.log(err),
          () => {console.log('bookTransaction returned '+name+'-count ='+ this.currentBookTranList.length);
              }

          );
    }
}

clickedFromAnbar(type:number){
    this.clickedAnbar = type;
}
selectAnbar(value:string){
    if(this.clickedAnbar == 1)
        this.selectedToAnbar = value;
    else 
        this.selectedAnbar = value;

    this.populateCurrentStock();
}
//selectAnbar,
selectedBookIndex(index: number){
    console.log('selectedBookIndex:: Selected book index '+ index );
    this.selectedBookTitle = this.bookListC[index].dataName;
    console.log('selectedBookIndex:: Selected book index '+  this.selectedBookTitle );
    this.populateCurrentStock();
}


ShowByBookName(){
       this.currentScreen = Screen.ebookFirst;
    this.bookFirst = true;
   this.placeFirst = false;
   this.addStock = false;
   this.sellBook = false;
   this.transferBook = false;
   this.getBookTranGroupBy();
}

ShowByLocation(){
    this.currentScreen = Screen.ebookFirst;
    this.bookFirst = false;
    this.placeFirst = true;
     this.sellBook = false;
    this.transferBook = false;
    this.addStock = false;
}
AddNewStock(){
    this.bookTranList.length = 0 ;
    this.addStock = true;
    this.bookFirst = false;
    this.placeFirst = false;
    this.sellBook = false;
    this.transferBook = false;
    this.currentScreen = Screen.eaddStock;
    this.selectedAnbar ='';
    this.selectedBookTitle ='';
}
SellStock(){
     this.bookTranList.length = 0 ;
     this.addStock = false;
     this.bookFirst = false;
     this.placeFirst = false;
     this.sellBook = true;
     this.transferBook = false;
     this.selectedAnbar ='';
    this.selectedBookTitle ='';
     this.currentScreen = Screen.esellBook;
     this.bookTranList.length = 0; 
}
TransferStock(){
     this.bookTranList.length = 0 ;
     this.addStock = false;
     this.bookFirst = false;
     this.placeFirst = false;
     this.sellBook = false;
     this.transferBook = true;
     this.selectedAnbar ='';
    this.selectedBookTitle ='';
     this.currentScreen = Screen.esellBook;
}

DonateStock(){
    this.donateBook = true; 
}

resetBookTran(){
    this.bookTranList.length = 0 ;
}

selectedOwner(name:string){
    this.selectedAnbar = name; 
    this.populateCurrentStock();
}

getBookTranGroupBy(){
          this.bs.getBookTranGroupBy().subscribe(
          data => this.bookCountList = data,
          err => console.log(err),
          () => {
              console.log('getBookTranGroupBy = count = '+this.bookCountList.length);
              }
                
          );
}
getAllBookNames()
{
          this.bs.getAllBookNames().subscribe(
          data => this.bookListC = data,
          err => console.log(err),
          () => {console.log('bookList Length returned ---'+this.bookList.length);
                console.log('bookList Image returned ---'+this.bookList[0].bookImgName);
                  console.log('bookList Name returned ---'+this.bookList[0].bookName);
              }
      );
}


submitNewStock(){

    console.log('submitNewStock total books '+ this.bookTranList.length);
    
    if(this.addStock){
        this.bs.addNewBook(this.bookTranList).subscribe(
            data => this.getData =  JSON.stringify(data),
            error => alert('ALERT __'+error),
            () => {this.bookTranList.length = 0;
                this.selectedAnbar = ''; 
                this.populateCurrentStock();
            }
        );
    }
    else if(this.sellBook){
         this.bs.sellBook(this.bookTranList).subscribe(
            data => this.getData =  JSON.stringify(data),
            error => alert('ALERT __'+error),
            () => {this.bookTranList.length = 0;
            this.populateCurrentStock();}
        );
    }
    else if(this.transferBook){
         this.bs.transferBook(this.tranferBookList).subscribe(
            data => this.getData =  JSON.stringify(data),
            error => alert('ALERT __'+error),
            () => {this.tranferBookList.length = 0;
            this.populateCurrentStock();}
        );
    }
}   

addBookCountToList(){
    if(!this.transferBook)
    {
        var found = false;
        this.bookTranList.forEach((element,index,arr) => {
            if(arr[index].bookName == this.selectedBookTitle &&
                arr[index].ownedBy == this.selectedAnbar){
                arr[index].count +=  this.selectedBookQty; 
                found = true;
            }
        });

        if(!found)
        {
            this.bookTran = new BookTran;
            this.bookTran.bookName = this.selectedBookTitle;
            this.bookTran.count = this.selectedBookQty;    
            this.bookTran.ownedBy = this.selectedAnbar;
            this.bookTran.location = this.selectedLocation;
            this.bookTran.bookTranId = 0;
            this.bookTranList.push(this.bookTran);
        }
        this.selectedBookTitle = "";
        this.selectedBookQty = 0;    
    }else{
        var found = false;
        this.tranferBookList.forEach((element,index,arr) => {
            if(arr[index].bookName == this.selectedBookTitle &&
                arr[index].fromOwner == this.selectedAnbar &&
                arr[index].toOwner == this.selectedToAnbar ){
                arr[index].count +=  this.selectedBookQty; 
                found = true;
            }
        });

        if(!found)
        {
            this.transferBookObj = new BookTransfer;
            this.transferBookObj.bookName = this.selectedBookTitle;
            this.transferBookObj.count = this.selectedBookQty;    
            this.transferBookObj.fromOwner = this.selectedAnbar;
            this.transferBookObj.toOwner = this.selectedToAnbar;
            this.tranferBookList.push(this.transferBookObj);
        }
        this.selectedBookTitle = "";
        this.selectedBookQty = 0;    
    }
}
clearBookTran(){
    this.selectedAnbar = '';
    this.selectedBookTitle = '';
    this.selectedBookQty = 0; 
    this.selectedLocation = '';
}
//used to select anbar name
valuechange(value:any){
    this.filterAnbar = value;
    var filtered = this.filterItems(value);
    this.anbarNameSelectedList = filtered; 
    console.log("Test Value : " + value );
}

filterItems(query:string) {
  return this.anbarList.filter(function(el) {
      return el.toLowerCase().indexOf(query) > -1;
  })
}

}