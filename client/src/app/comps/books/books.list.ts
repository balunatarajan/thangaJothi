import { Component } from '@angular/core';
import {BookService} from '../../services/books/book.services';

@Component({
  selector: 'book-list',
  templateUrl: '../../../tmpls/books/book-list.tmpl.html',
  //templateUrl: '../../../tmpls/anbar/anbar-list.tmpl.prime.html',
  providers: [BookService]
  //, // worked without this how?
  //styleUrls:['./list-comp.styles.css']
})
export class BookList { 
  placeNameList: Array<string>=[];
  bookNameList: Array<string>=[];
  trust = 'Thanga Jothi Gnana Sabai';
  nonEdit:boolean;
  placeFirst:boolean;
  bookFirst:boolean;
  addStock:boolean;
  constructor(){
      this.placeNameList.push("Hosur");
      this.placeNameList.push("Vadalur");
      this.placeNameList.push("Krishnamurthi");
      this.placeNameList.push("Saravanan");
      this.placeNameList.push("Babu");
      this.placeNameList.push("Logu");
      this.placeNameList.push("Coimbatore");

      this.bookNameList.push("Kanmani Maalai");
      this.bookNameList.push("Arulmani Maalai");
      this.bookNameList.push("SagaKalvi");
      this.bookNameList.push("Vallalyaar");
      this.bookNameList.push("Manthiramani Maalai");
      this.bookNameList.push("Gnanam Pera Vili");
      this.bookNameList.push("Anma neya orumaipaadu");
      this.nonEdit = false;
      this.bookFirst = true;
      this.placeFirst = false;
      this.addStock = false;

  }  
  
selectCall(selectedAnbar: string){
}

ShowByBookName(){
      this.bookFirst = true;
      this.placeFirst = false;
      this.addStock = false;
}

ShowByLocation(){
      this.bookFirst = false;
      this.placeFirst = true;
      this.addStock = false;
}
AddNewStock(){
    this.addStock = true;
    this.bookFirst = false;
     this.placeFirst = false;

}

}