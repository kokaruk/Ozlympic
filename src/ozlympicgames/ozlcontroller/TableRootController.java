package ozlympicgames.ozlcontroller;

/**
 * @author dimz
 * @since 22/5/17.
 */
public class TableRootController {

/*

    @FXML
    private void initialize() {
        showGameDetails(null);

    }


    void showGameDetails(OzlGame ozlGame) {
        if (ozlGame != null) {
       //     activeGame.setText(ozlGame.getId() + " " + GamesHelperFunctions.firsLetterToUpper(ozlGame.getGameSport().name()));
            ozlGame.addParticipant(getReferee());
            gamesDAL.get_athletes().values().stream()
                    .filter(gamesAthlete ->
                            gamesAthlete.getAthleteType().getSport().size() == 1 &&  gamesAthlete.getAthleteType().getSport().iterator().next() == ozlGame.getGameSport() ||  gamesAthlete.getAthleteType().getSport().size() > 1 )
                    .limit(8- ozlGame.getGameAthletes().size()).forEach(gamesAthlete -> ozlGame.addParticipant(gamesAthlete));

            try {
                ozlGame.gamePlay();
            } catch (NotEnoughAthletesException | NoRefereeException e) {
                Dialogues.createExceptionDialog(e);
            }


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
           // alert.initOwner(dialogStage);
            alert.setTitle("Game results");
            alert.setHeaderText("This is so hacky, its a shame :((( ");

            StringBuilder stringBuilder = new StringBuilder();

            for(String blah :ozlGame.get_referee().getGameScore(ozlGame)){
                String z = blah + "\r\n";
                stringBuilder.append(z );
            }
            alert.setContentText(stringBuilder.toString());

            alert.showAndWait();


        } else {

        }
    }


         /// this is so hacky, shame shame
        private GamesOfficial getReferee(){
            int refCount = gamesDAL.get_referees().size();
            int refs = GamesHelperFunctions.getRandomNumberInRange(0, refCount);
            List<GamesOfficial> refereeList = new ArrayList<>();
            refereeList.addAll(gamesDAL.get_referees().values());
            GamesOfficial referee = refereeList.get(refs);
            return referee;
        }
*/

}
