import {Injectable} from '@angular/core';
import {AnbarInfo} from '../../comps/anbar/anbar-info';
import {Count} from '../../comps/other/Count';
import {Http,Response,RequestOptions,RequestMethod,Headers} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
//import 'rxjx/add/operator/catch';

@Injectable()
export class AnbarService {

getData:string;
private serverIp:string ='blrlw4258';
private serverPort:string='4040';
private url : string = 'http://'+this.serverIp+':'+this.serverPort;

private getAnbarUrl: string = this.url+ '/tjgs/anbar/all';
private getAnbarByPageUrl: string = this.url+ '/tjgs/anbar/allbypage';

private postAddAnbarUrl: string = this.url+ '/tjgs/anbar/add';
private putAddAnbarUrl: string = this.url+ '/tjgs/anbar/modify';
private getCountUrl: string = this.url+'/tjgs/getCount'; 
constructor(private http : Http){}

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
            var headers = new Headers ({ 'Content-Type': 'application/json','Accept':'application/json' });
            //var options = new RequestOptions({headers: headers}, {method: RequestMethod.Post});
            let options = new RequestOptions({ headers: headers });
            //let test_this = {"search": "person"};
            var anbarToPost = JSON.stringify(ai);
            
            console.log('Post anbar : before service call : '+anbarToPost);
            //or = new Observable<Response>();
            return  this.http.post(this.postAddAnbarUrl, anbarToPost, options).
                                                map((res:any) => res.json());
                                                    
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
        console.log('URL ' + this.getAnbarByPageUrl+'?page='+pageNo+'&size='+pageSize);
        return this.http.get(this.getAnbarByPageUrl+'?page='+pageNo+'&size='+pageSize)
                    .map(this.extractAnbars);
}

GetAnbars() :Observable<AnbarInfo[]> {
         return this.http.get(this.getAnbarUrl)
                    .map(this.extractAnbars);
        //makes ajax request
}
    //utility method
extractAnbars(resp: Response){
        
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
}