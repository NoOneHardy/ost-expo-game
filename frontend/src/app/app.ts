import {Component, effect, inject, OnInit, Signal} from '@angular/core';
import {ViewModeService} from './services/view-mode.service'
import {ViewMode} from './model/view-mode'
import {Store} from '@ngrx/store'
import {selectViewMode} from "./store/root.feature";
import {count, map, Observable, timer} from 'rxjs'
import {AsyncPipe, DatePipe} from '@angular/common'
import {ScoreboardService} from './services/scoreboard.service'
import {TimeDifPipe} from './time-dif-pipe'

@Component({
  selector: 'ost-root',
  imports: [
    AsyncPipe,
    DatePipe,
    TimeDifPipe
  ],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit {
  private scoreboardService = inject(ScoreboardService)
  private viewModeService = inject(ViewModeService)
  private store = inject(Store)

  protected scoreboard$ = this.scoreboardService.getScores().pipe(
    map(scores => scores.slice(0, 10))
  )
  protected viewMode: Signal<ViewMode> = this.store.selectSignal(selectViewMode)
  protected start = new Date()

  constructor() {
    effect(() => {
      if (this.viewMode() === 'race') this.start = new Date()
    })
  }

  protected now = timer(0, 10).pipe(
    map(() => new Date())
  )

  timeDifference: Observable<number> = this.now.pipe(
    map(v => v.valueOf() - this.start.valueOf())
  )

  ngOnInit(): void {
    this.viewModeService.connect()
  }
}
