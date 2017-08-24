import {Injectable} from '@angular/core';
import {AnbarInfo} from '../../comps/anbar/anbar-info';
import {AnbarSearch} from '../../comps/anbar/anbarSearch';
import {Count} from '../../comps/other/Count';
import {Http,Response,RequestOptions,RequestMethod,Headers} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {MasterDataC} from '../../comps/other/masterData';
import 'rxjs/add/operator/map';
//import 'rxjx/add/operator/catch';

@Injectable()
export class AnbarService {
phpserver:number = 1;  // 0 - spring boot, 1 - local php , 2- remote php
getData:string;
private serverIp:string ='blrlw4258';
private serverPort:string='4040';
private url : string = 'http://'+this.serverIp+':'+this.serverPort;

private getAnbarUrl: string = this.url+ '/rest/tjgs/anbar/all';
private getAnbarAllUrl: string = this.url+ '/rest/tjgs/anbar/nameall';
private getAnbarByPageUrl: string = this.url+ '/rest/tjgs/anbar/allbypage';
private getGuruAllUrl: string = this.url+ '/rest/tjgs/guru/all';
private postAddAnbarUrl: string = this.url+ '/rest/tjgs/anbar/add';
private putAddAnbarUrl: string = this.url+ '/rest/tjgs/anbar/modify';
private getCountUrl: string = this.url+'/rest/tjgs/getCount'; 
private getAnbarByCrit: string = this.url+'/rest/tjgs/anbar/filterby'; 

constructor(private http : Http){}


public  getAnbarAllNames() :Observable<string[]>{
 
        console.log('Inside getAnbarAllNames .. ');
        return this.http.get(this.getAnbarAllUrl)
                  .map(this.extractCount);
}
public  getAnbarCountInDb() :Observable<Count> {
        //getCount?table=anbar
        console.log('Inside getAnbarCountInDb .. ');
        return this.http.get(this.getCountUrl+'?table=anbar')
                  .map(this.extractCount);
       // return this.http.get(this.getCountUrl+'?table=anbar')
       // .map((res:any) => res.json());
       //.map((res:any) => res.json());
}

extractCount(resp: Response){
        let body = resp.json();
        //console.log('Inside extractCount response.statusText:'+resp.statusText);
        //console.log('Inside extractCount response.'+ resp.json.toString());
        
        return body;
}
public modifyAnbar(ai:AnbarInfo){

            console.log('Inside Moidfy anbar service : userId' + ai.userId);
           // headers.append('Access-Control-Allow-Origin','*');

            var headers = new Headers ({ 'Content-Type': 'application/json','Accept':'application/json' });
            let options = new RequestOptions({ headers: headers });
            var anbarToPost = JSON.stringify(ai);
            
            console.log('Post anbar : before service call : '+anbarToPost);
            return  this.http.put(this.putAddAnbarUrl, anbarToPost, options).
                                                map((res:any) => res.json());
                                                
 }

public addNewAnbar(ai:AnbarInfo){
            console.log('Inside Add anbar service');
                
            //let authToken = this._user.getUser().JWT;
            //headers.append('Content-Type', 'application/json');
            //headers.append('Authorization', `Bearer ${authToken}`);
           let headers = new Headers({'Content-Type': 'application/json'});
           let options = new RequestOptions({headers: headers});
        
          // var headers = new Headers ({ 'Content-Type': 'application/json','Accept':'application/json' });
           // let options = new RequestOptions({ headers: headers, method: "post"  });
         
            var anbarToPost = JSON.stringify(ai);
            
            console.log('Post anbar : before service call : '+anbarToPost);
            var URL = this.postAddAnbarUrl;
            //or = new Observable<Response>();
            if(this.phpserver == 1){   
                URL = 'http://localhost:8089/createanbar.php';
                //when we pass options JSON is not reaching php
                return  this.http.post(URL, anbarToPost).
                                                map((res:any) => res.json());
            }
            else if(this.phpserver == 2) {

            }   
            else{
            return  this.http.post(URL, anbarToPost, options).
                                                map((res:any) => res.json());
            }                                   
            // testing 
            //this.url = 'http://date.jsontest.com';
            // return this.http.get(this.getAnbarUrl)
                //                    .map(res => res.json());
}

saveJwt(jwt: string) {
       if(jwt) localStorage.setItem('id_token', jwt)
}

logError(err: any) {
        console.log(err);
}


public getAnbarsByPage(pageSize : number,pageNo : number) :Observable<AnbarInfo[]> {
        // headers.append('Access-Control-Allow-Origin','*');

         var headers = new Headers ({ 'Content-Type': 'application/json','Accept':'application/json' });
         let options = new RequestOptions({ headers: headers });
        var url = this.getAnbarByPageUrl+'?page='+pageNo+'&size='+pageSize;
        if(this.phpserver == 1){   
                url = 'http://localhost:8089/getallanbargal.php'+'?page='+pageNo+'&size='+pageSize;
        }
        else if(this.phpserver == 2){   
                    url = 'http://apps.vallalyaar.com/getanbar.php'+'?page='+pageNo+'&size='+pageSize;
                    //url = 'http://apps.vallalyaar.com/json1.php';
        }
        return this.http.get(url).map(this.extractAnbars);
       
}


public getAllAnbarByCriteria(anbarSearch: AnbarSearch) :Observable<AnbarInfo[]> {
        var search = JSON.stringify(anbarSearch);
        var url = this.getAnbarByCrit+'?nameLike='+ anbarSearch.nameLike 
                                        +'&initLike='+ anbarSearch.initLike 
                                        +'&cityLike='+ anbarSearch.cityLike 
                                        +'&distLike='+anbarSearch.distLike
                                        +'&stateLike='+anbarSearch.stateLike
                                        +'&fDate='+anbarSearch.fDate
                                        +'&tDate='+anbarSearch.tDate;
        console.log('search ' + url);
        
        return this.http.get(url)
                    .map(this.extractAnbars);
}

//sort=name,asc&sort=numberOfHands,desc
//sort=<field>,asc&sort=<field>,desc
public getAllGuruNames() :Observable<MasterDataC[]> {
        console.log('URL ' + this.getGuruAllUrl);
        return this.http.get(this.getGuruAllUrl)
                    .map(this.extractAnbars);
}

GetAnbars() :Observable<AnbarInfo[]> {
         return this.http.get(this.getAnbarUrl)
                    .map(this.extractAnbars);
        //makes ajax request
}
    //utility method
extractAnbars(resp: Response){
        console.log('end ' + 'http://localhost:8089/getallanbargal.php');
        let body = resp.json();
        return body || [] ;  // if body is null return empty array []
}


public listAllAnbar  () {
         this.url = 'http://date.jsontest.com';
         return this.http.get(this.getAnbarUrl)
                            .map(res => res.json());  
}

public postJson(){

}

private extractData(res: Response) {
        let body = res.json();
        console.log('Response from server :'+body.data);
        return body.data || { };
    }

    /*private handleError (error: Response | any) {
        // In a real world app, you might use a remote logging infrastructure
        let errMsg: string;
        if (error instanceof Response) {
        const body = error.json() || '';
        const err = body.error || JSON.stringify(body);
        errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
        } else {
        errMsg = error.message ? error.message : error.toString();
        }
        console.error(errMsg);
        return Observable.throw(errMsg);
  }*/
public getAnbarsPhp() :Observable<AnbarInfo[]> {
        // headers.append('Access-Control-Allow-Origin','*');

         var headers = new Headers ({ 'Content-Type': 'application/json','Accept':'application/json' });
         let options = new RequestOptions({ headers: headers });
           
        return this.http.get('http://localhost:8089/getallanbargal.php')
                    .map(this.extractAnbars);
}


}