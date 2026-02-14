import {createFeature, createReducer, on} from '@ngrx/store'
import {ViewMode} from '../model/view-mode'
import {setScoreboardViewMode, setRaceViewMode} from './root.actions'

interface State {
  viewMode: ViewMode
}

const initialState: State = {
  viewMode: 'scoreboard',
}

export const rootFeature = createFeature({
  name: 'root',
  reducer: createReducer(
    initialState,
    on(setScoreboardViewMode, (state) => {
      return {
        ...state,
        viewMode: 'scoreboard'
      }
    }),
    on(setRaceViewMode, (state) => {
      return {
        ...state,
        viewMode: 'race'
      }
    })
  )
})

export const {
  selectViewMode
} = rootFeature
