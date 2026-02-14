import {inject, Injectable} from '@angular/core'
import {HttpClient} from '@angular/common/http'
import {map, Observable} from 'rxjs'
import {Score} from '../model/score'

@Injectable({
  providedIn: 'root',
})
export class ScoreboardService {
  private http = inject(HttpClient)

  public getScores(): Observable<Score[]> {
    return this.http.get<{ scores: Score[] }>('/api/scoreboard').pipe(
      map(res => res.scores)
    )
  }
}
