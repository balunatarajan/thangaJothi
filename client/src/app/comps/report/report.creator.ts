
import * as jsPDF from 'jspdf'
import {DatePipe} from '@angular/common';
import {CurrencyPipe} from '@angular/common';
import { Component } from '@angular/core';

import {TranCode} from '../trancode/tran-code';
import {TranInfo} from '../transaction/tran-info';
import {ReportData} from './report.data';

export class ReportCreator{

        trow:number = 0;
        pageNbr:number = 0;
        printVoucherNo:boolean = false;
        monthlySum: Array<ReportData>=[];
        consolidateOpenBalance:number = 0;
        
        constructor(private dp:DatePipe, 
                    private cp:CurrencyPipe,
                    private openingBalance:number,
                    private headerWise:boolean,
                    private year:number){
            
                    this.consolidateOpenBalance = this.openingBalance;
        }  

       //A4 width - 210, Height -297
       public genHeader(doc:jsPDF){
            
            doc.setFontSize(16);
            
            doc.text(10,15, 'THANGA JOTHI GNANA SABAI TRUST');
            doc.setFontSize(12);
            doc.text(10, 22, 'SAMARASA SUTHA SANMAARKA SATHIYA SANGAM');
            doc.setFontSize(11);
            doc.text(140, 15, 'No, 710/93, Karpagam Avenue,      ');
            doc.text(140, 20, 'Karpagam Nagar, Thottagiri Road,   ');
            doc.text(140, 25, 'Alasanatham, Hosur, TamilNadu');
            doc.text(140, 30, 'India - 635109');
            doc.text(140, 35, 'www.vallalyaar.com         ');
            doc.setFontSize(10);

            doc.rect(10, 38 , 190, 1, 'S');
            //console.log(      'REPORT SCREEN WIDTH'+doc.internal.pageSize.width);
            // console.log('REPORT SCREEN HEIGHT'+doc.internal.pageSize.height);
       }
       
      public genTitle(doc:jsPDF,title:string){
            // Title
            this.trow = 46;
            doc.setFontSize(13);
            doc.text(40, this.trow,title);
            if(!this.headerWise)
            {
                this.trow += 8;
                doc.setFontSize(10);
                doc.text(130, this.trow,'Opening Balance - Year  '+ this.year +'  =  Rs.'+this.openingBalance);
            }
            this.trow += 9;
            doc.setFontType("bold");
            doc.setFontSize(10);
            doc.text(10, this.trow,"Date");
            doc.text(35, this.trow,"Description");
           
            let col = 130; 
            if(!this.printVoucherNo)
                col +=25; 
           
            doc.text(col, this.trow,"Debit(Rs)");
            doc.text(col+25, this.trow,"Credit(Rs)");
            if(this.printVoucherNo)
                doc.text(col+25, this.trow,"VoucherNo");
        
            doc.setFontType("normal")
            this.pageNbr = 1;
            this.trow += 3;
      }

     public pageFooter(doc:jsPDF, pageNbr:number){
       // doc.setLineWidth(1);
        doc.rect(10, 280 , 190, 1,'S');
        doc.text(10, 285,"www.vallalyaar.com");
        doc.text(180, 285,"Page# "+pageNbr);
     }
      public genTitleSecondPageOnwards(doc:jsPDF, title:string){
            // Title
            this.trow = 20;
            //doc.setFont("arial");
            doc.setFontType("bold");
            doc.setFontSize(11);
            doc.text(70, this.trow,title);     
            //doc.line(65, this.trow+2,120 , this.trow+2); // horizontal line

            //doc.setFont("default");

            this.trow += 7;
            doc.setFontType("bold");
            doc.setFontSize(10);
            doc.text(10, this.trow,"Date");
            doc.text(35, this.trow,"Description");
            
            let col = 130; 
            if(!this.printVoucherNo)
                col +=25; 

            doc.text(col, this.trow,"Debit(Rs)");
            doc.text(col+25, this.trow,"Credit(Rs)");
            if(this.printVoucherNo)
                doc.text(col+50, this.trow,"VoucherNo");
            doc.setFontType("normal");
            this.pageNbr ++;
            this.trow += 8;
        }
        private printMonthlyBalance(doc:jsPDF,col:number,openBalance:number,drSum:number,crSum:number)
        {
                           doc.text(col,this.trow,   '(+)Opening balance for the month  = Rs  ');
                        doc.text(col+70,this.trow,''+this.intToFormat(''+openBalance));
                        
                        doc.text(col,this.trow+5, '(+)Total income for  the month        = Rs  ');
                        doc.text(col+70,this.trow+5,''+this.intToFormat(''+drSum));
                        
                        doc.text(col,this.trow+10, '(-)Total Expense for  the month      = Rs  ');
                        doc.text(col+70,this.trow+10,''+this.intToFormat(''+crSum));
                        
                        doc.line(col, this.trow+15,col+100 , this.trow+15); // horizontal line

                        doc.text(col,this.trow+20,'(=)Closing balance for the month    = Rs  ')
                        doc.text(col+70,this.trow+20,''+this.intToFormat(''+ this.consolidateOpenBalance));

        }
        public  populateDataMonthWise(doc:jsPDF,transList: Array<TranInfo>){
        
            this.trow += 5;
            var index = 0;
            var prevMonth = 0;
            var crSum = 0.0;
            var drSum = 0.0;
            var month = '';
            var runningAttribute = '';  // used to break by month and header
            var prevAtrribbute = '';
            var firstPageTitle = false;
            var openBalance = this.openingBalance;    
            var prevMonthStr = '';
            var tranMonth = '';
            transList.forEach((tran) => {
                //var datePipe = new DatePipe('en-US');
                let tranDate = this.dp.transform(tran.transDate, 'dd/MM/yyyy');
                prevMonthStr = tranMonth;
                tranMonth = this.dp.transform(tran.transDate, 'MMMM-yyyy');
                
                if(!firstPageTitle){    
                    var title = '';
                    if(!this.headerWise)
                        title = 'MONTH  : '+tranMonth;
                    else    
                        title = 'TRANSACTION HEADER : ' + tran.headerCode;
                    doc.setFontType("bold");
                    doc.setFontSize(11);
                    doc.text(70, 54,title);  // print samme line as - opening balance for the year  
                    //this.trow += 7;
                    doc.setFontSize(10);
                    doc.setFontType("normal");
                    firstPageTitle = true;
                }

                let endPt = tranMonth.indexOf('-'); 
                month = tranMonth.slice(0,endPt);

                if(!this.headerWise)
                     runningAttribute = month;//new Date(tran.transDate).getMonth() + '';
                else
                     runningAttribute = tran.headerCode;
                
                //A4 width - 210, Height -297
                if(prevAtrribbute == '' )  {
                    prevAtrribbute = runningAttribute;
                }   
                // Break the page on change of attribute
                if(prevAtrribbute != '' && !(runningAttribute == prevAtrribbute) )  {
                    // sum of credit/debit 
                    doc.setFontType("bold");
                    let col = 80;
                    if(!this.printVoucherNo)
                        col += 50;
                    doc.line(col, this.trow,col+70 , this.trow); // horizontal line
                    this.trow += 5 ;
                    doc.text(col, this.trow,'Total : ');
                    
                    col = 130; 
                    if(!this.printVoucherNo)
                        col +=25; 
                    doc.text(col, this.trow,this.intToFormat('' + drSum));
                    doc.text(col+25, this.trow,this.intToFormat('' + crSum));
                 
                    doc.setFontSize(10);
                    col = 35;
                    // need to do if it goes beyond page
                    this.trow += 8 ;
                    this.consolidateOpenBalance = this.consolidateOpenBalance + drSum - crSum; 
                    console.log('PRINT MONTHY BALANCE _ BREAK ATTRIBUTE'+this.trow);
                    console.log('Index while printing report - page break'+index);
                
                    if(index + 12 > 48 ) // add new page so that closing balance display goto next page
                    {
                        this.pageFooter(doc,this.pageNbr);
                        doc.addPage();
                        var title = '';
                        if(!this.headerWise)
                            title = 'MONTH  : '+prevMonthStr;
                        else    
                            title = 'TRANSACTION HEADER : ' + tran.headerCode;

                        this.genTitleSecondPageOnwards(doc,title);
                    }         
                
                    if(!this.headerWise){
                      this.printMonthlyBalance(doc,col,openBalance,drSum,crSum);
                    }
                    openBalance = this.consolidateOpenBalance ; 
                    
                    doc.setFontSize(10);
                    doc.setFontType("normal");
                    let rdata = new ReportData;

                    /*if(!this.headerWise)
                        rdata.month = prevAtrribbute;
                    else*/
                    rdata.month = prevAtrribbute; 
                    rdata.cSum = crSum;
                    rdata.dsum = drSum;
                    this.monthlySum.push(rdata);

                    prevAtrribbute = runningAttribute;
                    this.trow = 30;
                    index = 0;
                   
                    this.pageFooter(doc,this.pageNbr);
                    doc.addPage();
                    
                    var title = '';
                    if(!this.headerWise)
                        title = 'MONTH  : '+tranMonth;
                    else    
                        title = 'TRANSACTION HEADER : ' + tran.headerCode;

                    this.genTitleSecondPageOnwards(doc,title);
                    crSum = 0;
                    drSum = 0;
                }  
                {
                    doc.setFontSize(10);
                    //let setDot = this.dp?(this.dp).transform(tranDate, 'dd/MM/yyyy');
                    doc.text(10, this.trow,tranDate);
                    let desc = tran.description;
                    if(desc.length > 70)
                        desc = desc.slice(0,70);
                    doc.text(35, this.trow,desc );
                    let col = 130; 
                    if(!this.printVoucherNo)
                     col +=25; 
                    
                    let amt = this.intToFormat(tran.amount+'');
                    
                    if(tran.inExp == 'i'){
                          drSum += tran.amount;
                          doc.text(col, this.trow,''+amt);
                    }
                    else{
                        crSum += tran.amount;
                        doc.text(col+25, this.trow,''+ amt);
                    }
                     if(this.printVoucherNo)
                        doc.text(col+50, this.trow,tran.voucherNo);
                    
                    this.trow += 5; 
                    index++;
                    // Break the page at end
                    if(this.pageNbr == 1 && index >= 40  ||
                        this.pageNbr > 1 && index >= 48)
                    {
                        index = 0;
                        this.pageFooter(doc,this.pageNbr);
                        doc.addPage();
                        var title = '';
                        if(!this.headerWise)
                            title = 'MONTH  : '+tranMonth;
                        else    
                             title = 'TRANSACTION HEADER : ' + tran.headerCode;

                         this.genTitleSecondPageOnwards(doc,title);
                    }    
                }
            });
            
            // End of the report ..
            //this.trow += 2;
            doc.setFontType("bold");
            doc.line(130, this.trow,200 , this.trow); // horizontal line
            this.trow += 4;
            doc.text(130, this.trow,'Total : ');
            doc.text(155, this.trow,''+this.intToFormat(drSum+''));
            doc.text(180, this.trow,''+this.intToFormat(crSum+''));
             doc.setFontType("normal");

            doc.setFontSize(10);
                    // need to do if it goes beyond page
            this.trow += 10 ; 
            this.consolidateOpenBalance = this.consolidateOpenBalance + drSum - crSum; 
            let col = 35;
            //if(!this.headerWise)
            //doc.text(35,this.trow,'Closing balance for the Year = Rs. '+this.intToFormat(this.consolidateOpenBalance+''));
            console.log('PRINT MONTHLY BALANCE - '+this.trow);    
            if(!this.headerWise){
                                      this.printMonthlyBalance(doc,col,openBalance,drSum,crSum);
            }
            openBalance = this.consolidateOpenBalance ; 
            let rdata = new ReportData;
            if(this.headerWise)
                rdata.month = prevAtrribbute;
            else
                rdata.month = month;
            rdata.cSum = crSum;
            rdata.dsum = drSum;
            this.monthlySum.push(rdata);
            this.pageFooter(doc,this.pageNbr);
          
    }
    public  genMonthlyConsolidatedReport(doc:jsPDF){
          doc.addPage();
          this.genHeader(doc);
          this.trow = 50;
          doc.setFontType("bold");
          doc.setFontSize(14);
          let title = '';
          if(!this.headerWise)
             title ='Monthly Consolidated Credit/Debit for the year '+(this.year-1)+' - '+this.year;
          else
             title ='Monthly Consolidated Headerwise Report '+(this.year-1)+' - '+this.year;

          doc.text(40, this.trow, title);
          doc.setFontType("normal");
          
          doc.setFontSize(11);
          this.trow += 9;
          let col = 35;
          doc.line(col, this.trow,190 , this.trow); // horizontal line
          this.trow += 5;
          doc.text(col+5, this.trow,'Month');
          doc.text(col+55, this.trow,'Debit(Rs)');
          doc.text(col+105, this.trow,'Credit(Rs)');
          this.trow += 4;
          doc.line(col, this.trow,190 , this.trow); // horizontal line
          this.trow += 5;
          let tcSum = 0; 
          let tdSum = 0;
          this.monthlySum.forEach((mon) => {
              doc.text(col+5, this.trow, ''+mon.month);
              doc.text(col+55, this.trow, this.intToFormat(mon.cSum+''));
              doc.text(col+105, this.trow, this.intToFormat(mon.dsum+''));
              this.trow += 5;
              tcSum += mon.cSum;
              tdSum += mon.dsum;
          });

          doc.line(col, this.trow,190 , this.trow); // horizontal line
          this.trow += 5;
          doc.setFontType("bold");
          doc.text(col+5,this.trow,'Total');
          doc.text(col+55,this.trow,this.intToFormat(''+tdSum));
          doc.text(col+105,this.trow,this.intToFormat(''+tcSum));



          this.trow += 15;

          doc.text(col,this.trow,   '(+)Opening balance for the year  = Rs  ');
          doc.text(col+70,this.trow,''+this.intToFormat(''+this.openingBalance));
                        
          doc.text(col,this.trow+5, '(+)Total income for  the year        = Rs  ');
          doc.text(col+70,this.trow+5,''+this.intToFormat(''+tdSum));
                        
          doc.text(col,this.trow+10, '(-)Total Expense for  the year       = Rs  ');
          doc.text(col+70,this.trow+10,''+this.intToFormat(''+tcSum));
                        
          doc.line(col, this.trow+15,col+100 , this.trow+15); // horizontal line

          doc.text(col,this.trow+20,'(=)Closing balance for the year    = Rs  ')
          doc.text(col+70,this.trow+20,''+this.intToFormat(''+ (this.openingBalance + tdSum-tcSum)));

          doc.setFontType("normal");
          this.pageFooter(doc,this.pageNbr); 
      }

    //function for converting string into indian currency format
    private intToFormat(nStr:string)
    {
        
        let paise = '00';
        let rs = '0';
        let dotIndex = nStr.indexOf('.');
        // if dot is absent indexOf returns -1 
        if(dotIndex > 0)
        {    
            rs = nStr.slice(0,dotIndex-2);
            paise = nStr.slice(dotIndex+1,nStr.length);
            if(paise.length == 1 )
                paise = paise + '0';
            if(paise.length > 2) 
                 paise = paise.slice(0,2);
        }
        let rsInt = parseInt(nStr);
        //3000,3500,3050,3005
        let rs1000 = rsInt % 1000;
        rsInt = Math.trunc(rsInt / 1000);
        let rsFinal = rs1000 + ''; 
        //prepend 0
        
        if(rs1000 == 0)
            rsFinal = '000';
        else if(rs1000 >= 10 && rs1000 < 100 ) 
            rsFinal = '0' + rsFinal;
        else if(rs1000 < 10 ) 
            rsFinal = '00' + rsFinal;
       
        while(rsInt >= 1){
                // prepend
            let rsMod = rsInt % 100;
            rsInt = Math.trunc(rsInt / 100);

            if(rsMod == 0)
                rsFinal = '00,'+rsFinal;
            else if(rsInt > 0 && rsMod > 0 && rsMod < 10 )
                rsFinal = '0'+rsMod+','+rsFinal;
            else 
                rsFinal = rsMod+','+rsFinal;
            
        }

        let retVal = rsFinal+'.'+paise;
        /*let maxlen = 16; 
        let prepend = maxlen - retVal.length;
        while(prepend > 0)
        {
             retVal = ' ' + retVal;
             prepend --;
        }   
        console.log('retVal ['+retVal+']');*/
        return retVal;    
    }


}





/*doc.setFontSize(16);
            
            doc.text(60, 20, 'THANGA JOTHI GNANA SABAI TRUST');
            doc.setFontSize(14);
            doc.text(50, 27, 'SAMARASA SUTHA SANMAARKA SATHIYA SANGAM');
            doc.setFontSize(11);
            doc.text(75, 33, '    No, 710/93, Karpagam Avenue,      ');
            doc.text(75, 38, '   Karpagam Nagar, Thottagiri Road,   ');
            doc.text(75, 43, 'Alasanatham, Hosur, TamilNadu - 635109');
            doc.text(75, 48, '           www.vallalyaar.com         ');
            doc.setFontSize(10);*/
            // page border
            //doc.rect(10, 10, doc.internal.pageSize.width - 20, doc.internal.pageSize.height - 20, 'S');
