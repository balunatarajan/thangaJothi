import { Component } from '@angular/core';
import {AnbarInfo} from './anbar-info';
import {Count} from './../other/Count';
import {AnbarService} from '../../services/anbar/anbar.service';
import {AnbarSearch} from './anbarSearch';
import {MasterDataC} from './../other/masterData';

@Component({
  selector: 'anbar-list',
  templateUrl: '../../../tmpls/anbar/anbar-list.tmpl.html',
  //templateUrl: '../../../tmpls/anbar/anbar-list.tmpl.prime.html',
  providers: [AnbarService]
  //, // worked without this how?
  //styleUrls:['./list-comp.styles.css']
})
export class AnbarList { 

  trust = 'Thanga Jothi Gnana Sabai';
  getData : string;
  AnbarList: Array<AnbarInfo>=[];
  anbar: AnbarInfo;
  anbarInAdd: AnbarInfo;
  showDetail:number;
  currentPageNo : number;
  pageSize: number;
  anbarListCount : number;
  selectedItem : number;
  nonEdit:boolean;
  mode:string;
  totalRecords:number;
  pageList:Array<number> = [];//new Array(4);
  // pageList:number[] = new Array(4)  
  totalPages : number;
  anbarCount : Count = new Count;
  nameCityList:Array<string> = [];
  guruNames:Array<MasterDataC> = [];
  title:string;
  anbarSearch : AnbarSearch  = new AnbarSearch;
  filter_name:string;
  filter_guru:string;
  filter_city:string;
  filter_dist:string;
  filter_state:string;
  bFilter: boolean;

 constructor(private as:AnbarService) {

    this.showDetail = 0; 
    this.currentPageNo = 0;
    this.pageSize = 5;
    this.anbarListCount = 0;
    this.selectedItem = -1;
    this.nonEdit = true;
    this.anbarCount.count= "0";
    this.totalPages = this.totalRecords / this.pageSize; 
    this.populatePageArray();
    this.title = "Select Guru";
    this.bFilter = false;
  }
  
  ngOnInit(){
     this.getAnbarCountInDb();    //this.totalRecords = 59;
     this.totalPages = this.totalRecords / this.pageSize; 
     this.getAllAnbarCity();
     this.getAllGuruNames();
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
    onSelection(selectedAnbar: AnbarInfo){
      console.log('Radio button selected ');
      //this.anbar = selectedAnbar;
    }

    //selectPage event 
    selectPage(selectedPage:number)
    {
        console.log('page selected  ' + selectedPage);
        this.currentPageNo = selectedPage;
        this.getAnbarsByPage();
    }
    
    //<< >> button pressed
    setPageNumber(idx:number){
      this.showDetail = 0;
      if(idx == -1){
            if(this.currentPageNo > 0){
              this.currentPageNo --;
              this.getAnbarsByPage();
               this.populatePageArray();
            }
      }
      else if(idx == -2){
           if(this.currentPageNo >= 0 && this.currentPageNo + 4 < this.totalPages ){
              this.currentPageNo ++;
              this.getAnbarsByPage();

               this.populatePageArray();
            }
      }
     
    }

    listAllAnbar() {
          /*this.as.listAllAnbar()
                .subscribe(
                                data => this.getData =  JSON.stringify(data),
                                error => alert(error),
                                ()=>console.log("Finished")
                              );
            */        
    }
    
    getAnbarsByPage(){
          //this.AnbarList=[];
          this.AnbarList.length = 0;
          this.bFilter = false;
          this.anbarSearch.cityLike = "*";
          this.anbarSearch.distLike = "*";
          this.anbarSearch.initLike = "*";
          this.anbarSearch.nameLike = "*";
          this.anbarSearch.stateLike = "*";         
          if(this.filter_city){
                this.anbarSearch.cityLike = this.filter_city;
                this.bFilter = true;
          }
      
          if(this.filter_name){
                this.anbarSearch.nameLike = this.filter_name;
                this.bFilter = true;
          }
          if(this.filter_state){
                this.anbarSearch.stateLike = this.filter_state;
                this.bFilter = true;
          }

          if(this.filter_dist) {
                this.anbarSearch.distLike = this.filter_dist;
                this.bFilter = true;
          }

          if(this.filter_guru) {
                this.anbarSearch.initLike = this.filter_guru;
                this.bFilter = true;
          }

          if(this.bFilter) {
            console.log('anbar.list.ts : filter values city',this.filter_city);
            console.log('anbar.list.ts : filter values name ',this.filter_name);
            console.log('anbar.list.ts : filter values state',this.filter_state);
            console.log('anbar.list.ts : filter values dist',this.filter_dist);
            console.log('anbar.list.ts : filter values guru',this.filter_guru);

            this.as.getAllAnbarByCriteria(this.anbarSearch).subscribe(
            data => this.AnbarList = data,
            err => console.log(err),
            () =>{this.anbarListCount = AnbarList.length; 
                    this.totalPages = this.totalRecords / this.pageSize; 
                    this.populatePageArray();}
            );

          }
          else{  
              this.as.getAnbarsByPage(this.pageSize,this.currentPageNo).subscribe(
              data => this.AnbarList = data,
              err => console.log(err),
              () =>{this.anbarListCount = AnbarList.length; 
                    this.totalPages = this.totalRecords / this.pageSize; 
                    this.populatePageArray();}
               );
          }
          this.nonEdit = true;
          this.mode = '';
          this.showDetail = 0; 
          console.log('Total RECORDs'+this.totalRecords);
         
    }

    getAllAnbarCity()
    {
          this.as.getAllAnbarCityByPage().subscribe(
          data => this.nameCityList = data,
          err => console.log(err)
          );
    }

     getAllGuruNames()
    {
          this.as.getAllGuruNames().subscribe(
          data => this.guruNames = data,
          err => console.log(err)
          );
    }

    getAnbars()
    {
          console.log('anbar.list.ts : getAnbars before service');
          this.as.GetAnbars().subscribe(
          data => this.AnbarList = data,
          err => console.log(err)
          );
          
          //this.getData = JSON.stringify(this.AnbarList);         
         console.log('anbar.list.ts : getAnbars after service len = ' + this.AnbarList.length);
          
        for (let i in AnbarList) {
        console.log("Anbar List - "+AnbarList[i].userName);
      }  
    }
  // delete button
  deleteAnbar(){
  //make service && call list again to get new list 


  }
  //modify button  
  modifyAnbar(){
  
      if(this.showDetail != 1)
        alert("No Record Selected ");
      else {
      this.mode ='M';
      this.nonEdit = false;
      }
   } 
  
  //add button
  addNewAnbar(){

    if(this.showDetail != 1){
        this.nonEdit = true;
      //this.anbar = selectedAnbar;
      this.anbar = new AnbarInfo;  // deep copy 
      this.showDetail = 1;
    }
    
    this.mode ='A';
    this.resetAnbar();    
    this.nonEdit = false;
    
  }

  getAnbarCountInDb(){

     console.log('anbar.list.ts : getAnbarCountInDb');
     
     this.as.getAnbarCountInDb().subscribe(
          data => this.anbarCount = data,
          err => console.log('--ERROR---'+err),
          ()=>this.totalRecords = parseInt(this.anbarCount.count)
          );
  }

  //copy button
  copyNewAnbar(){
    if(this.showDetail != 1)
        alert("No Record Selected ");
    else
    {
      this.nonEdit = false;
      this.mode ='C';
      this.anbar.userName = '';
    }
  }

  //save button 
  saveButtonClicked() {
  
    if( this.mode == 'C' || this.mode == 'A'){
     
        this.as.addNewAnbar(this.anbar)
               .subscribe(
                          data => this.getData =  JSON.stringify(data),
                          error => alert('ALERT __'+error),
                          () => this.getAnbarsByPage()
                         );
                         
       
    }
    else if( this.mode =='M'){
        
        this.as.modifyAnbar(this.anbar)
               .subscribe(
                          data => this.getData =  JSON.stringify(data),
                          error => alert('ALERT __'+error),
                          ()=> this.getAnbarsByPage()
                         ); 
    } 

    this.nonEdit = true;
    this.mode = '';
    this.showDetail = 0; 
    
    //redraw the page 
  } 
  
  //cancel 
  cancelButtonClicked() {
    this.showDetail = 0;

  }
  private  resetAnbar(){

      this.anbar.address = '';
      this.anbar.city = '';
      this.anbar.country = '';
      this.anbar.dist = '';
      this.anbar.emailId = '';
      this.anbar.initThru = '';
      this.anbar.locality = '';
      this.anbar.phoneNo = '';
      this.anbar.state = '';
      this.anbar.userName = '';
   }

  
  selectCall(selectedAnbar: AnbarInfo){
  
    this.nonEdit = true;
    //this.anbar = selectedAnbar;
    this.anbar = Object.assign({}, selectedAnbar);  // deep copy 
    this.showDetail = 1;
    console.log('User Selected :'+selectedAnbar.userName + ":"+selectedAnbar.phoneNo);
  }

changed(event:any) {
    this.anbar.initThru = event;
  }

selectG(guruSelected:string){
  console.log('Guru Selected :'+guruSelected);
this.filter_guru = guruSelected;
}
}
