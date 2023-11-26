
cd /home/vigneshwaran/Personal/Projects/Backend/Authorization

echo "Starting AuthServer ...\n"
bash AuthServer/start-up.sh &

echo "Waiting for AuthServer ...\n"
sleep 4s

echo "Starting AuthResourceServer... \n"
bash AuthResourceServer/start-up.sh &

echo "Starting AuthClientApp ... \n"
bash AuthClient/start-up.sh &

